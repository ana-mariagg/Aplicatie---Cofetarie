
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SearchTorturiPanel {

    private JTextField txtDenumire;
    private JTextField txtStoc;
    private JTextField txtPret;
    private JButton saveButton;
    private JCheckBox is_active;

    public static JFrame frame_search_tort;
    private JPanel search_tort_panel;

    Tort tort_nou = new Tort();

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tort", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    void SetTextFields(Tort decoratiune) throws SQLException {
        txtDenumire.setText(decoratiune.getDenumire());
        txtStoc.setText(String.valueOf(decoratiune.getStoc()));
        txtPret.setText(String.valueOf(decoratiune.getPret()));
        is_active.setSelected(decoratiune.getActive());

        tort_nou.setId(DbUtils.FindCakeIndex(con, decoratiune.getDenumire()));
    }
    void SetNewDecoratiune()
    {
        tort_nou.setDenumire(txtDenumire.getText());
        tort_nou.setStoc(Integer.parseInt(txtStoc.getText()));
        tort_nou.setPret(Integer.parseInt(txtPret.getText()));
        tort_nou.setActive(is_active.isSelected());

    }

    public SearchTorturiPanel(Tort tort) throws Exception {

        Connect();
        SetTextFields(tort);
        //UI
        frame_search_tort = new JFrame("Decoratiune");
        frame_search_tort.setContentPane(search_tort_panel);
        frame_search_tort.setSize(350, 331);
        frame_search_tort.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconT.jpg");
        frame_search_tort.setIconImage(icon);

        saveButton.addActionListener(e -> {


            try {
                SetNewDecoratiune();
                DbUtils.UpdateTortInDB(con, tort_nou);
                TorturiMeniu.ShowAlteredRow();
                txtDenumire.setText("");
                txtStoc.setText("");
                txtPret.setText("");
                is_active.setSelected(false);

                frame_search_tort.setVisible(false);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Update failed!" );
            }
        });


    }
}
