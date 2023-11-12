import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class SearchClientiPanel {

    private JButton saveButton;
    private JTextField txtNume;
    private JTextField txtPrenume;
    private JTextField txtAdresa;
    private JTextField txtNrTelefon;
    private JTextField txtAnInregistrare;
    private JCheckBox is_active;
    private JButton searchButton;

    public static JFrame frame_search_clienti;
    private JPanel search_client_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    public SearchClientiPanel() throws Exception {

        Connect();

        //UI
        frame_search_clienti = new JFrame("Client");
        frame_search_clienti.setContentPane(search_client_panel);
        frame_search_clienti.setSize(350, 331);
        frame_search_clienti.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconCl.jpg");
        frame_search_clienti.setIconImage(icon);

        saveButton.addActionListener(e -> {
            Client client_nou = new Client();
            client_nou.setNume(txtNume.getText());
            client_nou.setPrenume(txtPrenume.getText());
            client_nou.setAdresa(txtAdresa.getText());
            client_nou.setNr_telefon(txtNrTelefon.getText());
            client_nou.setAn_inregistrare(Integer.parseInt(txtAnInregistrare.getText()));
            client_nou.setActive(is_active.isSelected());
            try {
                client_nou.setId(DbUtils.FindClientIndex(con, client_nou.getNume()));
                DbUtils.UpdateClientInDB(con, client_nou);
                ClientiMeniu.ShowAlteredRow();
                txtNume.setText("");
                txtPrenume.setText("");
                txtAdresa.setText("");
                txtNrTelefon.setText("");
                txtAnInregistrare.setText("");
                is_active.setSelected(false);

                frame_search_clienti.setVisible(false);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Update failed!" );
            }
        });

        searchButton.addActionListener(e -> {
            try {
                Client client_nou = new Client();
                String nume = txtNume.getText();
                client_nou = DbUtils.SearchClientInDB(con, nume);

                if (client_nou.getAn_inregistrare() != -1) {
                    txtNume.setText(nume);
                    txtPrenume.setText(client_nou.getPrenume());
                    txtAdresa.setText(client_nou.getAdresa());
                    txtNrTelefon.setText(client_nou.getNr_telefon());
                    txtAnInregistrare.setText(String.valueOf(client_nou.getAn_inregistrare()));
                    is_active.setSelected(client_nou.getActive());
                } else {
                    txtNume.setText("");
                    txtPrenume.setText("");
                    txtAdresa.setText("");
                    txtNrTelefon.setText("");
                    txtAnInregistrare.setText("");
                    is_active.setSelected(false);
                    JOptionPane.showMessageDialog(null, "Invalid client!");
                }
                frame_search_clienti.setVisible(true);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Invalid client!" );
            }
        });
    }
}

