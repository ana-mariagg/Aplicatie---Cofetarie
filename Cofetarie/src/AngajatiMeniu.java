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

    public class AngajatiMeniu extends JFrame {

        static Connection conn;
        static Vector<Angajat> angajati;
        static JTable tabel_angajati;
        static JFrame frame_add_anagajat;
        static JFrame frame_search_angajat;

        public static JFrame frame_tabel_angajati;

        //UI Components
        public static void InitUITabelAngajati() {

            //creating Frame
            frame_tabel_angajati = new JFrame("Angajati");
            frame_tabel_angajati.setSize(600, 460);
            frame_tabel_angajati.setLocationRelativeTo(null);

            //Adaugarea pictogramei
            Image icon = Toolkit.getDefaultToolkit().getImage("iconA.jpg");
            frame_tabel_angajati.setIconImage(icon);

            //Creating menu bar
            JMenuBar menu_bar = new JMenuBar();
            JMenu meniu = new JMenu("Operatii");

            JMenuItem optiune_adauga_angajat = new JMenuItem("Adauga angajat");
            meniu.add(optiune_adauga_angajat);

            JMenuItem optiune_stergere_angajat = new JMenuItem("Sterge angajatul selectat");
            meniu.add(optiune_stergere_angajat);

            JMenuItem optiune_cautare_angajat = new JMenuItem("Cauta & actualizeaza angajat");
            meniu.add(optiune_cautare_angajat);

            menu_bar.add(meniu);
            frame_tabel_angajati.setJMenuBar(menu_bar);

            //Adding actions to menu components
            optiune_adauga_angajat.addActionListener(e -> {
                frame_add_anagajat.setVisible(true);
            });

            optiune_stergere_angajat.addActionListener(e -> {
                int index_angajat_selected = tabel_angajati.getSelectedRow();
                if (index_angajat_selected == -1) {
                    System.out.println("No employee selected");
                    JOptionPane.showMessageDialog(null, "No employee selected!");
                    return;
                }

                int selected_empoyee_id = angajati.elementAt(index_angajat_selected).getId();
                try {
                    DbUtils.DeleteEmployeeFromDB(conn, selected_empoyee_id);
                } catch (Exception except) {
                    System.out.println("Deletion failed: " + except);
                    JOptionPane.showMessageDialog(null, "Deletion failed!");
                    return;
                }
                angajati.remove(index_angajat_selected);

                System.out.println(index_angajat_selected);
                tabel_angajati.getSelectionModel().removeSelectionInterval(index_angajat_selected, index_angajat_selected);

                try {
                    ((DefaultTableModel) tabel_angajati.getModel()).removeRow(index_angajat_selected);
                } catch (Exception except) {
                    System.out.println("Deletion failed ");
                }
            });

            optiune_cautare_angajat.addActionListener(e -> {

                int index_angajat_selected = tabel_angajati.getSelectedRow();
                if (index_angajat_selected == -1) {
                    System.out.println("No cookie selected");
                    JOptionPane.showMessageDialog(null, "No cookie selected!");
                    return;
                }

                Angajat angajat = angajati.elementAt(index_angajat_selected);
                try {
                    frame_search_angajat = new SearchAngajatiPanel(angajat).frame_search_angajat;
                    frame_search_angajat.setVisible(true);

                } catch (Exception except) {
                    System.out.println("Update failed: " + except);
                    JOptionPane.showMessageDialog(null, "Update failed! ");
                    return;
                }
                //frame_search_angajat.setVisible(true);
            });

            //filling table with data
            Object[][] data = new Object[angajati.size()][4];
            for (int index_angajat = 0; index_angajat < angajati.size(); index_angajat++) {
                data[index_angajat][0] = angajati.elementAt(index_angajat).getNume();
                data[index_angajat][1] = angajati.elementAt(index_angajat).getPrenume();
                data[index_angajat][2] = angajati.elementAt(index_angajat).getNr_telefon();
                data[index_angajat][3] = String.valueOf(angajati.elementAt(index_angajat).getSalariu());
            }

            String[] column = {"NUME", "PRENUME", "TELEFON", "SALARIU"};
            DefaultTableModel model = new DefaultTableModel(data, column);

            tabel_angajati = new JTable(model);
            tabel_angajati.setBounds(30, 40, 200, 300);
            JScrollPane scroll_panel = new JScrollPane(tabel_angajati);
            frame_tabel_angajati.add(scroll_panel);

            Color header_color = new Color(0x6672BF);
            tabel_angajati.getTableHeader().setBackground(header_color);

            //adding event listeners for the table
            tabel_angajati.getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent event) {
                    DefaultTableModel model = (DefaultTableModel) tabel_angajati.getModel();
                    int changed_angajat_index = event.getFirstRow();
                    model.getValueAt(changed_angajat_index, 0);

                    angajati.elementAt(changed_angajat_index).setNume(model.getValueAt(changed_angajat_index, 0).toString());
                    angajati.elementAt(changed_angajat_index).setPrenume(model.getValueAt(changed_angajat_index, 1).toString());
                    angajati.elementAt(changed_angajat_index).setNr_telefon(model.getValueAt(changed_angajat_index, 2).toString());
                    angajati.elementAt(changed_angajat_index).setSalariu(Integer.parseInt(model.getValueAt(changed_angajat_index, 3).toString()));
                    try {
                        DbUtils.UpdateAngajatInDB(conn, angajati.elementAt(changed_angajat_index));
                    } catch (Exception e) {
                        System.out.println("Update failed");
                        JOptionPane.showMessageDialog(null, "Update failed!");
                    }
                }
            });
        }

        //Database functions
        public static Connection ConnectToDB() throws Exception {
            //database connection
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        }

        public static void InsertAngajatInDB(Angajat angajat) throws Exception {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select insert_angajat('" + angajat.getNume() + "', '" +
                    angajat.getPrenume() + "', '" + angajat.getNr_telefon() + "', '" + angajat.getSalariu() + "')");

            rs.next();
            angajat.setId(Integer.parseInt(rs.getString("insert_angajat")));
            angajati.add(angajat);
            UpdateTable(angajat);
        }

        public AngajatiMeniu() throws Exception {
            conn = ConnectToDB();
            angajati = new Vector<Angajat>();
            DbUtils.ReadAngajatiFromDB(conn, angajati);

            //Initializing UI
            frame_add_anagajat = new AddAngajatPanel().frame_add_angajat;
            //frame_search_angajat = new SearchAngajatiPanel().frame_search_angajat;
            InitUITabelAngajati();
            frame_tabel_angajati.setVisible(true);
        }

        //auxilliary functions
        public static void UpdateTable(Angajat angajat_nou) {
            DefaultTableModel model = (DefaultTableModel) tabel_angajati.getModel();
            model.addRow(new Object[]{angajat_nou.getNume(), angajat_nou.getPrenume(),
                    angajat_nou.getNr_telefon(), angajat_nou.getSalariu()});
        }
        public static void ShowAlteredRow() throws Exception {       //#########################################
            frame_tabel_angajati.setVisible(false);
            angajati.clear();
            DbUtils.ReadAngajatiFromDB(conn, angajati);
            InitUITabelAngajati();
            frame_tabel_angajati.setVisible(true);
        }
    }

