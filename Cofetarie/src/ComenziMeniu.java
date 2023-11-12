import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class ComenziMeniu extends JFrame {

    static Connection conn;
    static Vector<Comanda> comenzi;
    static JTable tabel_comenzi;
    static JFrame frame_add_comanda;
    static JFrame frame_search_comanda;

    public static JFrame frame_tabel_comenzi;


    //UI Components
    public static void InitUITabelComenzi() {

        //creating Frame
        frame_tabel_comenzi = new JFrame("Comenzi");
        frame_tabel_comenzi.setSize(600, 460);
        frame_tabel_comenzi.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconCo.jpg");
        frame_tabel_comenzi.setIconImage(icon);

        //creating menu bar
        JMenuBar menu_bar = new JMenuBar();
        JMenu meniu = new JMenu("Operatii");

        JMenuItem optiune_adauga_comanda = new JMenuItem("Adauga comanda");
        meniu.add(optiune_adauga_comanda);

        JMenuItem optiune_sterge_comanda = new JMenuItem("Sterge comanda");
        meniu.add(optiune_sterge_comanda);

        JMenuItem optiune_cauta_comanda = new JMenuItem("Cauta & actualizeaza comanda");
        meniu.add(optiune_cauta_comanda);

        menu_bar.add(meniu);
        frame_tabel_comenzi.setJMenuBar(menu_bar);

        //adding actions to menu components
        optiune_adauga_comanda.addActionListener(e -> {
            frame_add_comanda.setVisible(true);
        });

        optiune_sterge_comanda.addActionListener(e -> {
            int selected_comanda_index = tabel_comenzi.getSelectedRow();
            if (selected_comanda_index == -1) {
                System.out.println("No order selected");
                JOptionPane.showMessageDialog(null, "No order selected!");
                return;
            }

            int selected_comanda_id = comenzi.elementAt(selected_comanda_index).getId();

            try {
                DbUtils.DeleteComandaFromDB(conn, selected_comanda_id);
            } catch (Exception except) {
                System.out.println("Deletion failed: " + except);
                JOptionPane.showMessageDialog(null, "Deletion failed! ");
                return;
            }
            comenzi.remove(selected_comanda_index);

            System.out.println(selected_comanda_index);
            System.out.println(tabel_comenzi.getRowCount());
            tabel_comenzi.getSelectionModel().removeSelectionInterval(selected_comanda_index, selected_comanda_index);

            try {
                ((DefaultTableModel) tabel_comenzi.getModel()).removeRow(selected_comanda_index);
            } catch (Exception except) {
                System.out.println("Deletion failed! ");
            }
        });

        optiune_cauta_comanda.addActionListener(e -> {
            frame_search_comanda.setVisible(true);
        });

        //filling table with student data
        Object[][] data = new Object[comenzi.size()][4];
        for (int index_comanda = 0; index_comanda < comenzi.size(); index_comanda++) {
            data[index_comanda][0] = String.valueOf(comenzi.elementAt(index_comanda).getNr_bucati());
            data[index_comanda][1] = comenzi.elementAt(index_comanda).getProdus();
            data[index_comanda][2] = String.valueOf(comenzi.elementAt(index_comanda).getPret());
            data[index_comanda][3] = comenzi.elementAt(index_comanda).getActive();
        }
        String[] column = {"NR BUCATI", "PRODUS", "PRET", "ACTIV"};
        DefaultTableModel model = new DefaultTableModel(data, column) {

            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        tabel_comenzi = new JTable(model);
        tabel_comenzi.setBounds(30, 40, 200, 300);
        JScrollPane scroll_panel = new JScrollPane(tabel_comenzi);
        frame_tabel_comenzi.add(scroll_panel);

        Color header_color = new Color(0xF16648);
        tabel_comenzi.getTableHeader().setBackground(header_color);

        //adding event listeners for the table
        tabel_comenzi.getModel().addTableModelListener(event -> {
            DefaultTableModel model1 = (DefaultTableModel) tabel_comenzi.getModel();
            int changed_comanda_index = event.getFirstRow();
            model1.getValueAt(changed_comanda_index, 0);

            comenzi.elementAt(changed_comanda_index).setNr_bucati(Integer.parseInt(model1.getValueAt(changed_comanda_index, 0).toString()));
            comenzi.elementAt(changed_comanda_index).setProdus(model1.getValueAt(changed_comanda_index, 1).toString());
            comenzi.elementAt(changed_comanda_index).setPret(Integer.parseInt(model1.getValueAt(changed_comanda_index, 2).toString()));
            comenzi.elementAt(changed_comanda_index).setActive((boolean) model1.getValueAt(changed_comanda_index, 3));

            try {
                DbUtils.UpdateComandaInDB(conn, comenzi.elementAt(changed_comanda_index));
            } catch (Exception e) {
                System.out.println("Update failed");
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        });
    }


    //Database functions
    public static Connection ConnectToDB() throws Exception {
        //database connection
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
    }


    public static void InsertComandaInDB(Comanda comanda) throws Exception {
        Statement st = conn.createStatement();

        String active_string;
        if (comanda.getActive())
            active_string = "true";
        else
            active_string = "false";

        ResultSet rs = st.executeQuery("select insert_comanda('" + comanda.getNr_bucati() + "', '" +
                comanda.getProdus() + "', '" + comanda.getPret() + "', '" + active_string + "')");

        rs.next();
        comanda.setId(Integer.parseInt(rs.getString("insert_comanda")));
        comenzi.add(comanda);
        UpdateTable(comanda);
    }


    public ComenziMeniu() throws Exception {
        conn = ConnectToDB();
        comenzi = new Vector<Comanda>();
        DbUtils.ReadComenziFromDB(conn, comenzi);

        //Initializing UI
        frame_add_comanda = new AddComandaPanel().frame_add_comanda;
        frame_search_comanda = new SearchComenziPanel().frame_search_comanda;
        InitUITabelComenzi();
        frame_tabel_comenzi.setVisible(true);
    }

    public static void ShowAlteredRow() throws Exception {       //#########################################
        frame_tabel_comenzi.setVisible(false);
        comenzi.clear();
        DbUtils.ReadComenziFromDB(conn, comenzi);
        InitUITabelComenzi();
        frame_tabel_comenzi.setVisible(true);
    }

    //auxilliary functions
    public static void UpdateTable(Comanda comanda_noua) {
        DefaultTableModel model = (DefaultTableModel) tabel_comenzi.getModel();
        model.addRow(new Object[]{comanda_noua.getNr_bucati(), comanda_noua.getProdus(),
                comanda_noua.getPret(), comanda_noua.getActive()});
    }
}
