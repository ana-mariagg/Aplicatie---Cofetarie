import javax.swing.*;
import java.awt.*;

public class Main {

    static JFrame frame_main;
    private JPanel main;
    public static void InitUIMain() {

        frame_main = new JFrame("Sugar & BLOOM");
        frame_main.setContentPane(new Main().main);
        frame_main.setSize(1000, 700);
        frame_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_main.setLocationRelativeTo(null);
        //Adaugarea background-ului pentru pagina de start
        JLabel background;
        frame_main.setLayout(null);
        ImageIcon img = new ImageIcon("home.jpeg");
        background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 1000, 670);
        background.setLayout(new FlowLayout());
        frame_main.add(background);

         //Adaugarea pictogramei
         Image icon = Toolkit.getDefaultToolkit().getImage("iconP.jpg");
        frame_main.setIconImage(icon);

        JButton adminPrajituri = new  JButton ("Prajituri");
        adminPrajituri.setBounds(0, 0, 120, 30);
        JButton adminTorturi = new  JButton ("Torturi");
        adminTorturi.setBounds(130, 0, 120, 30);
        JButton adminAngajati = new  JButton ("Angajati");
        adminAngajati.setBounds(260, 0, 120, 30);
        JButton adminClienti = new JButton("Clienti");
        adminClienti.setBounds(390,0,120,30);
        JButton adminComenzi = new JButton("Comenzi");
        adminComenzi.setBounds(520,0,120,30);

        background.add(adminPrajituri);
        background.add(adminTorturi);
        background.add(adminAngajati);
        background.add(adminClienti);
        background.add(adminComenzi);

        adminPrajituri.setVisible(true);
        adminTorturi.setVisible(true);
        adminAngajati.setVisible(true);
        adminClienti.setVisible(true);
        adminComenzi.setVisible(true);

        frame_main.setVisible(true);

        adminPrajituri.addActionListener(e -> {
            try {
                PrajituriMeniu meniu_praji = new PrajituriMeniu();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        adminTorturi.addActionListener(e -> {
            try {
                TorturiMeniu meniu_torturi = new TorturiMeniu();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        adminClienti.addActionListener(e -> {
            try {
                ClientiMeniu meniu_client = new ClientiMeniu();
                meniu_client.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        adminAngajati.addActionListener(e -> {
            try {
                AngajatiMeniu meniu_ang = new AngajatiMeniu();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        adminComenzi.addActionListener(e -> {
            try {
                ComenziMeniu meniu_com = new ComenziMeniu();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        //Initializing UI
        InitUIMain();
    }
}