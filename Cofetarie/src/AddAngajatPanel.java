
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class AddAngajatPanel {

    private JTextField txtNume;
    private JTextField txtPrenume;
    private JTextField txtTelefon;
    private JTextField txtSalariu;
    private JButton saveButton;

    public static JFrame frame_add_angajat;
    private JPanel add_angajat_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    public AddAngajatPanel() throws Exception {

        Connect();

        //UI
        frame_add_angajat = new JFrame("Angajat");
        frame_add_angajat.setContentPane(add_angajat_panel);
        frame_add_angajat.setSize(350, 331);
        frame_add_angajat.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconA.jpg");
        frame_add_angajat.setIconImage(icon);

        saveButton.addActionListener(e -> {

            Angajat angajat_nou = new Angajat();

            if (!Objects.equals(txtNume.getText(), "") && !Objects.equals(txtPrenume.getText(), "") &&
                    !Objects.equals(txtTelefon.getText(), "") && !Objects.equals(txtSalariu.getText(), "")) {

                angajat_nou.setNume(txtNume.getText());
                angajat_nou.setPrenume(txtPrenume.getText());
                angajat_nou.setNr_telefon(txtTelefon.getText());
                angajat_nou.setSalariu(Integer.parseInt(txtSalariu.getText()));

            } else {
                JOptionPane.showMessageDialog(null, "Empty fields!");
            }
            try {
                if (!Objects.equals(angajat_nou.getNume(), "") && !Objects.equals(angajat_nou.getPrenume(), "") &&
                        angajat_nou.getSalariu() != 0) {

                    AngajatiMeniu.InsertAngajatInDB(angajat_nou);
                    frame_add_angajat.setVisible(false);

                    txtNume.setText("");
                    txtPrenume.setText("");
                    txtTelefon.setText("");
                    txtSalariu.setText("");
                }
            } catch (Exception except) {
                System.out.println(except);
            }
        });
    }
}
