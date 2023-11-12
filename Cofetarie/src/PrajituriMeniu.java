import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class PrajituriMeniu extends JFrame{
        static Connection conn;
        static Vector<Prajitura> prajituri;
        static JTable tabel_praji;
        static JFrame frame_add_praji;
        static JFrame frame_search_praji;

        public static JFrame frame_tabel_praji;

        //UI Components
        public static void InitUITabelPraji() {

            //creating Frame
            frame_tabel_praji = new JFrame("Prajituri");
            frame_tabel_praji.setSize(800, 640);
            frame_tabel_praji.setLocationRelativeTo(null);

            //Adaugarea pictogramei
            Image icon = Toolkit.getDefaultToolkit().getImage("iconP.jpg");
            frame_tabel_praji.setIconImage(icon);


            //creating menu bar
            JMenuBar menu_bar = new JMenuBar();
            JMenu meniu = new JMenu("Operatii");

            JMenuItem optiune_adauga_praji = new JMenuItem("Adauga prajitura");
            meniu.add(optiune_adauga_praji);

            JMenuItem optiune_stergere_praji = new JMenuItem("Sterge prajitura selectata");
            meniu.add(optiune_stergere_praji);

            JMenuItem optiune_cautare_praji = new JMenuItem("Cauta & actualizeaza prajitura");
            meniu.add(optiune_cautare_praji);

            menu_bar.add(meniu);
            frame_tabel_praji.setJMenuBar(menu_bar);

            //adding actions to menu components
            optiune_adauga_praji.addActionListener(e -> {
                frame_add_praji.setVisible(true);
            });

            optiune_stergere_praji.addActionListener(e -> {

                int index_cookie_selected = tabel_praji.getSelectedRow();
                if (index_cookie_selected == -1) {
                    System.out.println("No cookie selected");
                    JOptionPane.showMessageDialog(null, "No cookie selected!");
                    return;
                }

                int selected_praji_id = prajituri.elementAt(index_cookie_selected).getId();
                try {
                    DbUtils.DeletePrajiFromDB(conn, selected_praji_id);
                } catch (Exception except) {
                    System.out.println("Deletion failed: " + except);
                    JOptionPane.showMessageDialog(null, "Deletion failed! ");
                    return;
                }

                prajituri.remove(index_cookie_selected);

                System.out.println(index_cookie_selected);
                tabel_praji.getSelectionModel().removeSelectionInterval(index_cookie_selected, index_cookie_selected);

                try {
                    ((DefaultTableModel) tabel_praji.getModel()).removeRow(index_cookie_selected);
                } catch (Exception except) {
                    System.out.println("Deletion failed! ");
                }
            });

            optiune_cautare_praji.addActionListener(e -> {

                int index_praji_selected = tabel_praji.getSelectedRow();
                if (index_praji_selected == -1) {
                    System.out.println("No cookie selected");
                    JOptionPane.showMessageDialog(null, "No cookie selected!");
                    return;
                }

                Prajitura praji = prajituri.elementAt(index_praji_selected);
                try {
                    frame_search_praji = new SearchPrajituriPanel(praji).frame_search_praji;
                    frame_search_praji.setVisible(true);

                } catch (Exception except) {
                    System.out.println("Update failed: " + except);
                    JOptionPane.showMessageDialog(null, "Update failed! ");
                    return;
                }
                //frame_search_floare.setVisible(true);
            });

            //filling table with data
            Object[][] data = new Object[prajituri.size()][4];
            for (int index_cookie = 0; index_cookie < prajituri.size(); index_cookie++) {
                data[index_cookie][0] = prajituri.elementAt(index_cookie).getDenumire();
                data[index_cookie][1] = prajituri.elementAt(index_cookie).getStoc();
                data[index_cookie][2] = prajituri.elementAt(index_cookie).getPret();
                data[index_cookie][3] = prajituri.elementAt(index_cookie).getActive();
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

            tabel_praji = new JTable(model);
            tabel_praji.setBounds(30, 40, 200, 300);
            JScrollPane scroll_panel = new JScrollPane(tabel_praji);
            frame_tabel_praji.add(scroll_panel);

            Color header_color = new Color(0x9766BF);
            tabel_praji.getTableHeader().setBackground(header_color);


            //adding event listeners for the table
            tabel_praji.getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent event) {
                        DefaultTableModel model = (DefaultTableModel) tabel_praji.getModel();
                    int changed_cookie_index = event.getFirstRow();
                    model.getValueAt(changed_cookie_index, 0);

                    prajituri.elementAt(changed_cookie_index).setDenumire(model.getValueAt(changed_cookie_index, 0).toString());
                    prajituri.elementAt(changed_cookie_index).setStoc(Integer.parseInt(model.getValueAt(changed_cookie_index, 1).toString()));
                    prajituri.elementAt(changed_cookie_index).setPret(Integer.parseInt(model.getValueAt(changed_cookie_index, 2).toString()));
                    prajituri.elementAt(changed_cookie_index).setActive((boolean) model.getValueAt(changed_cookie_index, 3));
                    try {
                       DbUtils.UpdatePrajituraInDB(conn, prajituri.elementAt(changed_cookie_index));
                    } catch (Exception e) {
                        System.out.println("Update failed! ");
                        JOptionPane.showMessageDialog(null, "Update failed! ");
                    }
                }
            });
        }

        //Database functions
        public static Connection ConnectToDB() throws Exception {

            //database connection
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        }

        public PrajituriMeniu() throws Exception {
            conn = ConnectToDB();
            prajituri = new Vector<Prajitura>();
            DbUtils.ReadPrajiFromDB(conn, prajituri);

            //Initializing UI
            //!!!!!!!!!!!!!!!!!!!!!!!!!1
            frame_add_praji = new AddPrajituriPanel().frame_add_praji;
           // frame_search_praji = new SearchPrajituriPanel().frame_search_praji;
            InitUITabelPraji();
            frame_tabel_praji.setVisible(true);
        }


        //auxilliary functions
        public static void UpdateTable(Prajitura praji_noua) {
            DefaultTableModel model = (DefaultTableModel) tabel_praji.getModel();
            model.addRow(new Object[]{praji_noua.getDenumire(), praji_noua.getStoc(),
                    praji_noua.getPret(), praji_noua.getActive()});
        }

    public static void ShowAlteredRow() throws Exception {       //#########################################
        frame_tabel_praji.setVisible(false);
        prajituri.clear();
        DbUtils.ReadPrajiFromDB(conn, prajituri);
        InitUITabelPraji();
        frame_tabel_praji.setVisible(true);
    }

        public static void InsertPrajiIntoDB(Prajitura CoOkie) throws Exception {
            Statement st = conn.createStatement();
            String active_string;
            if (CoOkie.getActive())
                active_string = "true";
            else
                active_string = "false";

            ResultSet rs = st.executeQuery("select insert_praji('" + CoOkie.getDenumire() + "', '" +
                    CoOkie.getStoc() + "', '" + CoOkie.getPret() + "', '" + active_string + "')");

            rs.next();
            CoOkie.setId(Integer.parseInt(rs.getString("insert_prajitura")));
            prajituri.add(CoOkie);
            UpdateTable(CoOkie);
        }


}
