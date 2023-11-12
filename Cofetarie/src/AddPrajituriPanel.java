
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class AddPrajituriPanel {

    private JTextField txtDenumire;
    private JTextField txtStoc;
    private JTextField txtPret;
    private JButton saveButton;
    private JCheckBox is_active;

    public static JFrame frame_add_praji;
    private JPanel add_praji_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    public AddPrajituriPanel() throws Exception {

        Connect();

        //UI
        frame_add_praji = new JFrame("Prajituri");
        frame_add_praji.setContentPane(add_praji_panel);
        frame_add_praji.setSize(350, 331);
        frame_add_praji.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconP.jpg");
        frame_add_praji.setIconImage(icon);

        saveButton.addActionListener(e -> {

            Prajitura praji_noua = new Prajitura();

            if (!Objects.equals(txtDenumire.getText(), "") && !Objects.equals(txtStoc.getText(), "") &&
                    !Objects.equals(txtPret.getText(), "") && is_active.isSelected()) {

                praji_noua.setDenumire(txtDenumire.getText());
                praji_noua.setStoc(Integer.parseInt(txtStoc.getText()));
                praji_noua.setPret(Integer.parseInt(txtPret.getText()));
                praji_noua.setActive(is_active.isSelected());

            } else {
                JOptionPane.showMessageDialog(null, "Empty fields");
            }

            try {
                if (!Objects.equals(praji_noua.getDenumire(), "") && praji_noua.getStoc() != 0 &&
                        praji_noua.getPret() != 0 && praji_noua.getActive()) {

                    PrajituriMeniu.InsertPrajiIntoDB(praji_noua);
                    frame_add_praji.setVisible(false);

                    txtDenumire.setText("");
                    txtStoc.setText("");
                    txtPret.setText("");
                    is_active.setSelected(false);
                }

            } catch (Exception except) {
                System.out.println(except);
            }
        });
    }
}
