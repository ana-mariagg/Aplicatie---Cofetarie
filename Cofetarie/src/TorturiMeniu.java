import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
        import java.awt.*;
        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.Statement;
        import java.util.Vector;

public class TorturiMeniu extends JFrame {

    static Connection conn;
    static Vector<Tort> torturi;
    static JTable tabel_torturi;
    static JFrame frame_add_tort;
    static JFrame frame_search_tort;

    public static JFrame frame_tabel_tort;



    //UI Components
    public static void InitUITabelTorturi() {

        //Creating Frame
        frame_tabel_tort = new JFrame("Torturi");
        frame_tabel_tort.setSize(600, 460);
        frame_tabel_tort.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconT.jpg");
        frame_tabel_tort.setIconImage(icon);

        //Creating menu bar
        JMenuBar menu_bar = new JMenuBar();
        JMenu meniu = new JMenu("Operatii");

        JMenuItem optiune_adauga_tort = new JMenuItem("Adauga tort");
        meniu.add(optiune_adauga_tort);

        JMenuItem optiune_sterge_tort = new JMenuItem("Sterge tort");
        meniu.add(optiune_sterge_tort);

        JMenuItem optiune_cautare_tort = new JMenuItem("Cauta & actualizeaza tort");
        meniu.add(optiune_cautare_tort);

        menu_bar.add(meniu);
        frame_tabel_tort.setJMenuBar(menu_bar);

        //adding actions to menu components
        optiune_adauga_tort.addActionListener(e -> {
            frame_add_tort.setVisible(true);
        });

        optiune_sterge_tort.addActionListener(e -> {
            int selected_tort_index = tabel_torturi.getSelectedRow();
            if (selected_tort_index == -1) {
                System.out.println("No cake selected");
                JOptionPane.showMessageDialog(null, "No tort selected!");
                return;
            }
            int selected_tort_id = torturi.elementAt(selected_tort_index).getId();
            try {
                DbUtils.DeleteTortFromDB(conn, selected_tort_id);
            } catch (Exception except) {
                System.out.println("Deletion failed: " + except);
                JOptionPane.showMessageDialog(null, "Deletion failed!");
                return;
            }
            torturi.remove(selected_tort_index);

            System.out.println(selected_tort_index);
            tabel_torturi.getSelectionModel().removeSelectionInterval(selected_tort_index, selected_tort_index);

            try {
                ((DefaultTableModel) tabel_torturi.getModel()).removeRow(selected_tort_index);
            } catch (Exception except) {
                System.out.println("Deletion failed " );
            }
        });

        optiune_cautare_tort.addActionListener(e -> {

            int selected_tort_index = tabel_torturi.getSelectedRow();
            if (selected_tort_index == -1) {
                System.out.println("No cake selected");
                JOptionPane.showMessageDialog(null, "No cake selected!");
                return;
            }
            Tort tort = torturi.elementAt(selected_tort_index);

            try {
                frame_search_tort = new SearchTorturiPanel(tort).frame_search_tort;
                frame_search_tort.setVisible(true);

            } catch (Exception except) {
                System.out.println("Update failed: " + except);
                JOptionPane.showMessageDialog(null, "Update failed! ");
                return;
            }

            //frame_search_decoratiune.setVisible(true);
        });

        //filling table with data
        Object[][] data = new Object[torturi.size()][4];
        for (int index_tort = 0; index_tort < torturi.size(); index_tort++) {
            data[index_tort][0] = torturi.elementAt(index_tort).getDenumire();
            data[index_tort][1] = torturi.elementAt(index_tort).getStoc();
            data[index_tort][2] = torturi.elementAt(index_tort).getPret();
            data[index_tort][3] = torturi.elementAt(index_tort).getActive();
        }

        String[] column = {"DENUMIRE", "STOC", "PRET", "ACTIV"};
        DefaultTableModel model = new DefaultTableModel(data, column) {

            public Class<?> getColumnClass(int column) {
                if (column == 3) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        tabel_torturi = new JTable(model);
        tabel_torturi.setBounds(30, 40, 200, 300);
        JScrollPane scroll_panel = new JScrollPane(tabel_torturi);
        frame_tabel_tort.add(scroll_panel);

        Color header_color = new Color(0x66BF9D);
        tabel_torturi.getTableHeader().setBackground(header_color);

        //adding event listeners for the table
        tabel_torturi.getModel().addTableModelListener(event -> {
            DefaultTableModel model1 = (DefaultTableModel) tabel_torturi.getModel();
            int changed_praji_index = event.getFirstRow();
            model1.getValueAt(changed_praji_index, 0);

            torturi.elementAt(changed_praji_index).setDenumire(model1.getValueAt(changed_praji_index, 0).toString());
            torturi.elementAt(changed_praji_index).setStoc(Integer.parseInt(model1.getValueAt(changed_praji_index, 1).toString()));
            torturi.elementAt(changed_praji_index).setPret(Integer.parseInt(model1.getValueAt(changed_praji_index, 2).toString()));
            torturi.elementAt(changed_praji_index).setActive((boolean) model1.getValueAt(changed_praji_index, 3));
            try {
                DbUtils.UpdateTortInDB(conn, torturi.elementAt(changed_praji_index));
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


    public static void InsertTortIntoDB(Tort tort) throws Exception {
        Statement st = conn.createStatement();

        String active_string;
        if (tort.getActive())
            active_string = "true";
        else
            active_string = "false";

        ResultSet rs = st.executeQuery("select insert_decoratiune('" + tort.getDenumire() + "', '" +
                tort.getStoc() + "', '" + tort.getPret() + "', '" + active_string + "')");

        rs.next();
        tort.setId(Integer.parseInt(rs.getString("insert_decoratiune")));
        torturi.add(tort);
        UpdateTable(tort);
    }

    public TorturiMeniu() throws Exception {
        conn = ConnectToDB();
        torturi = new Vector<Tort>();
        DbUtils.ReadTorturiFromDB(conn, torturi);

        //Initializing UI
        frame_add_tort = new AddTortPanel().frame_add_tort;

        InitUITabelTorturi();
        frame_tabel_tort.setVisible(true);
    }

    //auxilliary functions
    public static void UpdateTable(Tort tort_nou) {
        DefaultTableModel model = (DefaultTableModel) tabel_torturi.getModel();
        model.addRow(new Object[]{tort_nou.getDenumire(), tort_nou.getStoc(),
                tort_nou.getPret(), tort_nou.getActive()});
    }

    public static void ShowAlteredRow() throws Exception {       //#########################################
        frame_tabel_tort.setVisible(false);
        torturi.clear();
        DbUtils.ReadTorturiFromDB(conn, torturi);
        InitUITabelTorturi();
        frame_tabel_tort.setVisible(true);
    }
}
