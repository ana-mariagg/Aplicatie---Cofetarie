
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class AddTortPanel {

    private JTextField txtDenumire;
    private JTextField txtStoc;
    private JTextField txtPret;
    private JButton saveButton;
    private JCheckBox is_active;

    public static JFrame frame_add_tort;
    private JPanel add_tort_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    public AddTortPanel() throws Exception {

        Connect();

        //UI
        frame_add_tort = new JFrame("Tort");
        frame_add_tort.setContentPane(add_tort_panel);
        frame_add_tort.setSize(350, 331);
        frame_add_tort.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconT.jpg");
        frame_add_tort.setIconImage(icon);

        saveButton.addActionListener(e -> {

            Tort decoratiune_noua = new Tort();

            if (!Objects.equals(txtDenumire.getText(), "") && !Objects.equals(txtStoc.getText(), "") &&
                    !Objects.equals(txtPret.getText(), "") && is_active.isSelected()) {

                decoratiune_noua.setDenumire(txtDenumire.getText());
                decoratiune_noua.setStoc(Integer.parseInt(txtStoc.getText()));
                decoratiune_noua.setPret(Integer.parseInt(txtPret.getText()));
                decoratiune_noua.setActive(is_active.isSelected());

            } else {
                JOptionPane.showMessageDialog(null, "Empty fields");
            }

            try {
                if (!Objects.equals(decoratiune_noua.getDenumire(), "") && decoratiune_noua.getStoc() != 0 &&
                        decoratiune_noua.getPret() != 0 && decoratiune_noua.getActive()) {

                    TorturiMeniu.InsertTortIntoDB(decoratiune_noua);
                    frame_add_tort.setVisible(false);

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
