
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class AddClientPanel {

    private JTextField txtNume;
    private JTextField txtPrenume;
    private JTextField txtAdresa;
    private JTextField txtNrTelefon;
    private JTextField txtAnInregistrare;
    private JButton saveButton;
    private JCheckBox is_active;

    public static JFrame frame_add_client;
    private JPanel add_client_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    public AddClientPanel() throws Exception {

        Connect();

        //UI
        frame_add_client = new JFrame("Client");
        frame_add_client.setContentPane(add_client_panel);
        frame_add_client.setSize(350, 331);
        frame_add_client.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconCl.jpg");
        frame_add_client.setIconImage(icon);

        saveButton.addActionListener(e -> {

            Client client_nou = new Client();

            if (!Objects.equals(txtNume.getText(), "") && !Objects.equals(txtPrenume.getText(), "") &&
                    !Objects.equals(txtAdresa.getText(), "") && !Objects.equals(txtNrTelefon.getText(), "") &&
                    !Objects.equals(txtAnInregistrare.getText(), "") && is_active.isSelected()) {

                client_nou.setNume(txtNume.getText());
                client_nou.setPrenume(txtPrenume.getText());
                client_nou.setAdresa(txtAdresa.getText());
                client_nou.setNr_telefon(txtNrTelefon.getText());
                client_nou.setAn_inregistrare(Integer.parseInt(txtAnInregistrare.getText()));
                client_nou.setActive(is_active.isSelected());

            } else {
                JOptionPane.showMessageDialog(null, "Empty fields!");
            }
            try {
                if (!Objects.equals(client_nou.getNume(), "") && !Objects.equals(client_nou.getPrenume(), "") &&
                        !Objects.equals(client_nou.getAdresa(), "") && !Objects.equals(client_nou.getNr_telefon(), "") &&
                        client_nou.getAn_inregistrare() != -1 && client_nou.getActive()) {

                    ClientiMeniu.InsertClientIntoDB(client_nou);
                    frame_add_client.setVisible(false);

                    txtNume.setText("");
                    txtPrenume.setText("");
                    txtAdresa.setText("");
                    txtNrTelefon.setText("");
                    txtNrTelefon.setText("");
                    txtAnInregistrare.setText("");
                    is_active.setSelected(false);
                }

            } catch (Exception except) {
                System.out.println(except);
            }
        });
    }
}
