
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SearchPrajituriPanel {

    private JTextField txtDenumire;
    private JTextField txtStoc;
    private JTextField txtPret;
    private JButton saveButton;
    private JCheckBox is_active;

    public static JFrame frame_search_praji;
    private JPanel search_praji_panel;

    private Prajitura prajitura_noua= new Prajitura();

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    void SetTextFields(Prajitura prajitura) throws  SQLException{
        txtDenumire.setText(prajitura.getDenumire());
        txtStoc.setText(String.valueOf(prajitura.getStoc()));
        txtPret.setText(String.valueOf(prajitura.getPret()));
        is_active.setSelected(prajitura.getActive());

        prajitura_noua.setId(DbUtils.FindCookieIndex(con, prajitura.getDenumire()));
    }
    void SetNewPraji()
    {
        prajitura_noua.setDenumire(txtDenumire.getText());
        prajitura_noua.setStoc(Integer.parseInt(txtStoc.getText()));
        prajitura_noua.setPret(Integer.parseInt(txtPret.getText()));
        prajitura_noua.setActive(is_active.isSelected());

    }

    public SearchPrajituriPanel(Prajitura prajitura) throws Exception {


        Connect();
        SetTextFields(prajitura);
        //UI
        frame_search_praji = new JFrame("Prajitura");
        frame_search_praji.setContentPane(search_praji_panel);
        frame_search_praji.setSize(350, 331);
        frame_search_praji.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconP.jpg");
        frame_search_praji.setIconImage(icon);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Floare floare_noua = new Floare();
                floare_noua.setDenumire(txtDenumire.getText());
                floare_noua.setStoc(Integer.parseInt(txtStoc.getText()));
                floare_noua.setPret(Integer.parseInt(txtPret.getText()));
                floare_noua.setActive(is_active.isSelected());*/
                try {
                    /*floare_noua.setId(DbUtils.FindFlowerIndex(con, floare_noua.getDenumire()));*/
                    SetNewPraji();
                    DbUtils.UpdatePrajituraInDB(con, prajitura_noua);
                    PrajituriMeniu.ShowAlteredRow();
                    txtDenumire.setText("");
                    txtStoc.setText("");
                    txtPret.setText("");
                    is_active.setSelected(false);
                    frame_search_praji.setVisible(false);
                } catch (Exception except) {
                    System.out.println(except);
                    JOptionPane.showMessageDialog(null, "Update failed!");
                }
            }
        });



        /*searchButton.addActionListener(e -> {
            try {
                Floare floare = new Floare();
                String denumire = txtDenumire.getText();
                floare = DbUtils.SearchFlowerInDB(con, denumire);

                if (floare.getPret() != 0) {
                    txtDenumire.setText(denumire);
                    txtStoc.setText(String.valueOf(floare.getStoc()));
                    txtPret.setText(String.valueOf(floare.getPret()));
                    is_active.setSelected(floare.getActive());

                } else {
                    txtDenumire.setText("");
                    txtStoc.setText("");
                    txtPret.setText("");
                    is_active.setSelected(false);
                    JOptionPane.showMessageDialog(null, "Invalid flower!");
                }
                frame_search_floare.setVisible(true);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Invalid flower!");
            }
        });
    }*/


    }


}
