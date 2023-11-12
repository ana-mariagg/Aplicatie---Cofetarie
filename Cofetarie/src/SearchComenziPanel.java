import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class SearchComenziPanel {

    private JTextField txtNrBucati;
    private JTextField txtPret;
    private JTextField txtProdus;
    private JButton saveButton;
    private JCheckBox is_active;
    private JButton searchButton;

    public static JFrame frame_search_comanda;
    private JPanel search_comanda_panel;

    Connection con;

    //Database connection
    public void Connect() throws Exception {
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cofetarie", "postgres", "1234");
        System.out.println("Conectat cu succes la baza de date.");
    }

    public SearchComenziPanel() throws Exception {

        Connect();

        //UI
        frame_search_comanda = new JFrame("Comanda");
        frame_search_comanda.setContentPane(search_comanda_panel);
        frame_search_comanda.setSize(350, 331);
        frame_search_comanda.setLocationRelativeTo(null);

        //Adaugarea pictogramei
        Image icon = Toolkit.getDefaultToolkit().getImage("iconCo.jpg");
        frame_search_comanda.setIconImage(icon);

        saveButton.addActionListener(e -> {
            Comanda comanda_noua = new Comanda();
            comanda_noua.setNr_bucati(Integer.parseInt(txtNrBucati.getText()));
            comanda_noua.setProdus(txtProdus.getText());
            comanda_noua.setPret(Integer.parseInt(txtPret.getText()));
            comanda_noua.setActive(is_active.isSelected());
            try {
                comanda_noua.setId(DbUtils.FindOrderIndex(con, comanda_noua.getProdus()));
                DbUtils.UpdateComandaInDB(con, comanda_noua);
                ComenziMeniu.ShowAlteredRow();
                txtProdus.setText("");
                txtNrBucati.setText("");
                txtPret.setText("");
                is_active.setSelected(false);

                frame_search_comanda.setVisible(false);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Update failed!" );
            }
        });

        searchButton.addActionListener(e -> {
            try {
                Comanda comanda = new Comanda();
                int nr_buc = Integer.parseInt(txtNrBucati.getText());
                comanda = DbUtils.SearchOrderInDB(con, nr_buc);

                if (comanda.getPret() != -1) {
                    txtProdus.setText(comanda.getProdus());
                    txtNrBucati.setText(String.valueOf(comanda.getNr_bucati()));
                    txtPret.setText(String.valueOf(comanda.getPret()));
                    is_active.setSelected(comanda.getActive());
                } else {
                    txtProdus.setText("");
                    txtNrBucati.setText("");
                    txtPret.setText("");
                    is_active.setSelected(false);
                    JOptionPane.showMessageDialog(null, "Invalid order!");
                }
                frame_search_comanda.setVisible(true);
            } catch (Exception except) {
                System.out.println(except);
                JOptionPane.showMessageDialog(null, "Invalid order!" );
            }
        });
    }
}

