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

    public class ClientiMeniu extends JFrame {

        static Connection conn;
        static Vector<Client> clienti;
        static JTable tabel_clienti;
        static JFrame frame_add_clienti;
        static JFrame frame_search_clienti;

        public static JFrame frame_tabel_clienti;

        //UI Components
        public static void InitUITabelClienti() {

            //creating Frame
            frame_tabel_clienti = new JFrame("Clienti");
            frame_tabel_clienti.setSize(600, 460);
            frame_tabel_clienti.setLocationRelativeTo(null);

            //Adaugarea pictogramei
            Image icon = Toolkit.getDefaultToolkit().getImage("iconCl.jpg");
            frame_tabel_clienti.setIconImage(icon);

            //Creating menu bar
            JMenuBar menu_bar = new JMenuBar();
            JMenu meniu = new JMenu("Operatii");

            JMenuItem optiune_adauga_client = new JMenuItem("Adauga client");
            meniu.add(optiune_adauga_client);

            JMenuItem optiune_stergere_client = new JMenuItem("Sterge clientul selectat");
            meniu.add(optiune_stergere_client);

            JMenuItem optiune_cautare_client = new JMenuItem("Cauta & actualizeaza client");
            meniu.add(optiune_cautare_client);

            menu_bar.add(meniu);
            frame_tabel_clienti.setJMenuBar(menu_bar);

            //adding actions to menu components
            optiune_adauga_client.addActionListener(e -> {
                frame_add_clienti.setVisible(true);
            });

            optiune_stergere_client.addActionListener(e -> {

                int index_client_selected = tabel_clienti.getSelectedRow();
                if (index_client_selected == -1) {
                    System.out.println("No client selected!");
                    return;
                }

                int selected_employee_id = clienti.elementAt(index_client_selected).getId();
                try {
                    DbUtils.DeleteClientFromDB(conn, selected_employee_id);
                } catch (Exception except) {
                    System.out.println("Deletion failed: " + except);
                    JOptionPane.showMessageDialog(null, "Deletion failed!");
                    return;
                }
                clienti.remove(index_client_selected);

                System.out.println(index_client_selected);
                tabel_clienti.getSelectionModel().removeSelectionInterval(index_client_selected, index_client_selected);

                try {
                    ((DefaultTableModel) tabel_clienti.getModel()).removeRow(index_client_selected);
                } catch (Exception except) {
                    System.out.println("Deletion failed! ");
                }
            });

            optiune_cautare_client.addActionListener(e -> {
                frame_search_clienti.setVisible(true);
            });

            //filling table with data
            Object[][] data = new Object[clienti.size()][6];
            for (int index_client = 0; index_client < clienti.size(); index_client++) {
                data[index_client][0] = clienti.elementAt(index_client).getNume();
                data[index_client][1] = clienti.elementAt(index_client).getPrenume();
                data[index_client][2] = clienti.elementAt(index_client).getNr_telefon();
                data[index_client][3] = clienti.elementAt(index_client).getAdresa();
                data[index_client][4] = String.valueOf(clienti.elementAt(index_client).getAn_inregistrare());
                data[index_client][5] = clienti.elementAt(index_client).getActive();
            }

            String[] column = {"NUME", "PRENUME", "TELEFON", "ADRESA", "AN INREGISTRARE", "ACTIV"};
            DefaultTableModel model = new DefaultTableModel(data, column) {

                public Class<?> getColumnClass(int column) {
                    if (column == 5) {
                        return Boolean.class;
                    }
                    return String.class;
                }
            };

            tabel_clienti = new JTable(model);
            tabel_clienti.setBounds(30, 40, 200, 300);
            JScrollPane scroll_panel = new JScrollPane(tabel_clienti);
            frame_tabel_clienti.add(scroll_panel);

            Color header_color = new Color(0xEFB436);
            tabel_clienti.getTableHeader().setBackground(header_color);

            //Adding event listeners for the clients table
            tabel_clienti.getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent event) {
                    DefaultTableModel model = (DefaultTableModel) tabel_clienti.getModel();
                    int changed_client_index = event.getFirstRow();
                    model.getValueAt(changed_client_index, 0);

                    clienti.elementAt(changed_client_index).setNume(model.getValueAt(changed_client_index, 0).toString());
                    clienti.elementAt(changed_client_index).setPrenume(model.getValueAt(changed_client_index, 1).toString());
                    clienti.elementAt(changed_client_index).setNr_telefon(model.getValueAt(changed_client_index, 2).toString());
                    clienti.elementAt(changed_client_index).setAdresa(model.getValueAt(changed_client_index, 3).toString());
                    clienti.elementAt(changed_client_index).setAn_inregistrare(Integer.parseInt(model.getValueAt(changed_client_index, 4).toString()));
                    clienti.elementAt(changed_client_index).setActive((boolean) model.getValueAt(changed_client_index, 5));
                    try {
                        DbUtils.UpdateClientInDB(conn, clienti.elementAt(changed_client_index));
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

        public static void InsertClientIntoDB(Client client) throws Exception {
            Statement st = conn.createStatement();

            String active_string;
            if (client.getActive())
                active_string = "true";
            else
                active_string = "false";

            ResultSet rs = st.executeQuery("select insert_client('" + client.getNume() + "', '" +
                    client.getPrenume() + "', '" + client.getNr_telefon() + "', '" + client.getAdresa()
                    + "', '" + client.getAn_inregistrare() + "', '" + active_string + "')");

            rs.next();
            client.setId(Integer.parseInt(rs.getString("insert_client")));
            clienti.add(client);
            UpdateTable(client);
        }

        public ClientiMeniu() throws Exception {
            conn = ConnectToDB();
            clienti = new Vector<Client>();
            DbUtils.ReadClientiFromDB(conn, clienti);

            //Initializing UI
            frame_add_clienti = new AddClientPanel().frame_add_client;
            frame_search_clienti = new SearchClientiPanel().frame_search_clienti;
            InitUITabelClienti();
            frame_tabel_clienti.setVisible(true);
        }

        public static void ShowAlteredRow() throws Exception {       //#########################################
            frame_tabel_clienti.setVisible(false);
            clienti.clear();
            DbUtils.ReadClientiFromDB(conn, clienti);
            InitUITabelClienti();
            frame_tabel_clienti.setVisible(true);
        }

        //auxilliary functions
        public static void UpdateTable(Client client_nou) {
            DefaultTableModel model = (DefaultTableModel) tabel_clienti.getModel();
            model.addRow(new Object[]{client_nou.getNume(), client_nou.getPrenume(),
                    client_nou.getNr_telefon(), client_nou.getAdresa(), client_nou.getAn_inregistrare(),
                    client_nou.getActive()});
        }
    }

