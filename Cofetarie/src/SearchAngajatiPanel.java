import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SearchAngajatiPanel {

    private JTextField txtNume;
    private JTextField txtPrenume;
    private JTextField txtTelefon;
    private JTextField txtSalariu;
    private JButton saveButton;

    private  Angajat angajat_nou = new Angajat();

    public static JFrame frame_search_angajat;
    private JPanel search_angajat_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }
    void SetTextFields(Angajat angajat) throws SQLException {
        txtNume.setText(angajat.getNume());
        txtPrenume.setText(angajat.getPrenume());
        txtTelefon.setText(angajat.getNr_telefon());
        txtSalariu.setText(String.valueOf(angajat.getSalariu()));

        angajat_nou.setId(DbUtils.FindEmployeeIndex(con, angajat.getNume()));
    }
    void SetNewAnagajat()
    {
        angajat_nou.setNume(txtNume.getText());
        angajat_nou.setPrenume(txtPrenume.getText());
        angajat_nou.setNr_telefon(txtTelefon.getText());
        angajat_nou.setSalariu(Integer.parseInt(txtSalariu.getText()));

    }
    public SearchAngajatiPanel(Angajat angajat) throws Exception {

        Connect();
        SetTextFields(angajat);

        //UI
        frame_search_angajat = new JFrame("Angajat");
        frame_search_angajat.setContentPane(search_angajat_panel);
        frame_search_angajat.setSize(350, 331);
        frame_search_angajat.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconA.jpg");
        frame_search_angajat.setIconImage(icon);

        saveButton.addActionListener(e -> {
            try {
                SetNewAnagajat();
                DbUtils.UpdateAngajatInDB(con, angajat_nou);
                AngajatiMeniu.ShowAlteredRow();
                txtNume.setText("");
                txtPrenume.setText("");
                txtTelefon.setText("");
                txtSalariu.setText("");

                frame_search_angajat.setVisible(false);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Update failed!");
            }
        });

        /*searchButton.addActionListener(e -> {
            try {
                Angajat angajat = new Angajat();
                String nume = txtNume.getText();
                angajat = DbUtils.SearchEmployeeInDB(con, nume);

                if (angajat.getSalariu() != 0) {
                    txtNume.setText(nume);
                    txtPrenume.setText(angajat.getPrenume());
                    txtTelefon.setText(angajat.getNr_telefon());
                    txtSalariu.setText(String.valueOf(angajat.getSalariu()));

                } else {
                    txtNume.setText("");
                    txtPrenume.setText("");
                    txtTelefon.setText("");
                    txtSalariu.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid employee!");
                }
                frame_search_angajat.setVisible(true);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Invalid employee!");
            }
        });
    */

    }
}
