
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class AddComandaPanel {

    private JTextField txtProdus;
    private JTextField txtPret;
    private JTextField txtNrBucati;
    private JButton saveButton;
    private JCheckBox is_active;

    public static JFrame frame_add_comanda;
    private JPanel add_comanda_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    public AddComandaPanel() throws Exception {

        Connect();

        //UI
        frame_add_comanda = new JFrame("Comanda");
        frame_add_comanda.setContentPane(add_comanda_panel);
        frame_add_comanda.setSize(350, 331);
        frame_add_comanda.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconCo.jpg");
        frame_add_comanda.setIconImage(icon);

        saveButton.addActionListener(e -> {

            Comanda comanda_noua = new Comanda();

            if ( !Objects.equals(txtProdus.getText(), "") && !Objects.equals(txtPret.getText(), "")
                    && !Objects.equals(txtNrBucati.getText(), "") && is_active.isSelected()) {


                comanda_noua.setProdus(txtProdus.getText());
                comanda_noua.setPret(Integer.parseInt(txtPret.getText()));
                comanda_noua.setNr_bucati(Integer.parseInt(txtNrBucati.getText()));
                comanda_noua.setActive(is_active.isSelected());

            } else {
                JOptionPane.showMessageDialog(null, "Empty fields!");
            }

            try {
                if ( !Objects.equals(comanda_noua.getProdus(), "") && comanda_noua.getNr_bucati() !=-1&&
                        comanda_noua.getPret() != -1 && comanda_noua.getActive()) {

                    ComenziMeniu.InsertComandaInDB(comanda_noua);
                    frame_add_comanda.setVisible(false);


                    txtProdus.setText("");
                    txtPret.setText("");
                    txtNrBucati.setText("");
                    is_active.setSelected(false);
                }
            } catch (Exception except) {
                System.out.println(except);
            }
        });
    }
}
