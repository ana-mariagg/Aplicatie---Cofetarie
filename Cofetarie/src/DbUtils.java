import java.sql.*;
import java.util.Vector;

public class DbUtils {

    private static PreparedStatement pst;

    //Read functions
    public static void ReadPrajiFromDB(Connection conn, Vector<Prajitura> prajituri) throws Exception {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select get_all_praji()");//apelam functia din postgres ce ne returneaza un tabel cu toate praji
        while (rs.next()) {
            String info_praji = (rs.getString("get_all_praji"));
            String[] splits = info_praji.replaceAll("\\(|\\)|\"", "").split("\s*,\s*");//parsing praji info

            boolean active;
            if (splits[4].charAt(0) == 'p')
                active = false;
            else
                active = true;

            Prajitura praji = new Prajitura(Integer.parseInt(splits[0]), splits[1], Integer.parseInt(splits[2]), Integer.parseInt(splits[3]), active);
            prajituri.add(praji);
        }
    }

    public static void ReadTorturiFromDB(Connection conn, Vector<Tort> torturi) throws Exception {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select get_all_torturi()");//apelam functia din postgres ce ne returneaza un tabel cu toate troturile
        while (rs.next()) {
            String info_tort = (rs.getString("get_all_torturi"));
            String[] splits = info_tort.replaceAll("\\(|\\)|\"", "").split("\s*,\s*");//parsing tort info

            boolean active;
            if (splits[4].charAt(0) == 'f')
                active = false;
            else
                active = true;

            Tort cake = new Tort(Integer.parseInt(splits[0]), splits[1], Integer.parseInt(splits[2]), Integer.parseInt(splits[3]), active);
            torturi.add(cake);
        }
    }

    public static void ReadClientiFromDB(Connection conn, Vector<Client> clienti) throws Exception {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select get_all_clienti()");//apelam functia din postgres ce ne returneaza un tabel cu toti clientii
        while (rs.next()) {
            String info_client = (rs.getString("get_all_clienti"));
            String[] splits = info_client.replaceAll("\\(|\\)|\"", "").split("\s*,\s*");//parsing client info

            boolean active;
            if (splits[6].charAt(0) == 'f')
                active = false;
            else
                active = true;

            Client client = new Client(Integer.parseInt(splits[0]), splits[1], splits[2], splits[3], splits[4], Integer.parseInt(splits[5]), active);
            clienti.add(client);
        }
    }

    public static void ReadAngajatiFromDB(Connection conn, Vector<Angajat> angajati) throws Exception {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select get_all_angajati()");//apelam functia din postgres ce ne returneaza un tabel cu toti angajatii
        while (rs.next()) {
            String info_angajat = (rs.getString("get_all_angajati"));
            String[] splits = info_angajat.replaceAll("\\(|\\)|\"", "").split("\s*,\s*");//parsing angajat info

            Angajat ang = new Angajat(Integer.parseInt(splits[0]), splits[1], splits[2], splits[3], Integer.parseInt(splits[4]));
            angajati.add(ang);
        }
    }

    public static void ReadComenziFromDB(Connection conn, Vector<Comanda> comenzi) throws Exception {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select get_all_comenzi()");//apelam functia din postgres ce ne returneaza un tabel cu toate comenzile
        while (rs.next()) {
            String info_comanda = (rs.getString("get_all_comenzi"));
            String[] splits = info_comanda.replaceAll("\\(|\\)|\"", "").split("\s*,\s*");//parsing comanda info

            boolean active;
            if (splits[4].charAt(0) == 'f')
                active = false;
            else
                active = true;

            Comanda com = new Comanda(Integer.parseInt(splits[0]), Integer.parseInt(splits[1]), splits[2], Integer.parseInt(splits[3]), active);
            comenzi.add(com);
        }
    }


    //Update functions
    public static void UpdatePrajituraInDB(Connection conn, Prajitura praji) throws Exception {
        Statement st = conn.createStatement();

        String active_string;
        if (praji.getActive())
            active_string = "true";
        else
            active_string = "false";

        st.executeQuery("select update_prajitura_info("
                + praji.getId() + ", '" + praji.getDenumire() + "', '" +
                praji.getStoc() + "', '" + praji.getPret() + "', '" + active_string + "')");
    }

    public static void UpdateTortInDB(Connection conn, Tort tort) throws Exception {
        Statement st = conn.createStatement();
        String active_string;
        if (tort.getActive())
            active_string = "true";
        else
            active_string = "false";

        st.executeQuery("select update_tort_info("
                + tort.getId() + ", '" + tort.getDenumire() + "', '" +
                tort.getStoc() + "', '" + tort.getPret() + "', '" + active_string + "')");
    }

    public static void UpdateClientInDB(Connection conn, Client client) throws Exception {
        Statement st = conn.createStatement();

        String active_string;
        if (client.getActive())
            active_string = "true";
        else
            active_string = "false";

        st.executeQuery("select update_client_info("
                + client.getId() + ", '" + client.getNume() + "', '" + client.getPrenume()
                + "', '" + client.getNr_telefon() + "', '" + client.getAdresa() +
                "', '" + client.getAn_inregistrare() +
                "', '" + active_string + "')");
    }

    public static void UpdateAngajatInDB(Connection conn, Angajat angajat) throws Exception {
        Statement st = conn.createStatement();
        st.executeQuery("select update_angajat_info("
                + angajat.getId() + ", '" + angajat.getNume() + "', '" +
                angajat.getPrenume() + "', '" + angajat.getNr_telefon() + "', '" + angajat.getSalariu() + "')");
    }

    public static void UpdateComandaInDB(Connection conn, Comanda comanda) throws Exception {
        Statement st = conn.createStatement();

        String active_string;
        if (comanda.getActive())
            active_string = "true";
        else
            active_string = "false";

        st.executeQuery("select update_comanda_info("
                + comanda.getId() + ", '" + comanda.getNr_bucati() + "', '" +
                comanda.getProdus() + "', '" + comanda.getPret() +
                "', '" + active_string + "')");
    }


    //Delete function
    public static void DeletePrajiFromDB(Connection conn, int praji_id) throws Exception {
        Statement st = conn.createStatement();
        st.executeQuery("select delete_praji(" + praji_id + ")");
    }

    public static void DeleteClientFromDB(Connection conn, int client_id) throws Exception {
        Statement st = conn.createStatement();
        st.executeQuery("select delete_client(" + client_id + ")");
    }

    public static void DeleteTortFromDB(Connection conn, int tort_id) throws Exception {
        Statement st = conn.createStatement();
        st.executeQuery("select delete_tort(" + tort_id + ")");
    }

    public static void DeleteEmployeeFromDB(Connection conn, int angajat_id) throws Exception {
        Statement st = conn.createStatement();
        st.executeQuery("select delete_angajat(" + angajat_id + ")");
    }

    public static void DeleteComandaFromDB(Connection conn, int comanda_id) throws Exception {
        Statement st = conn.createStatement();
        st.executeQuery("select delete_comanda(" + comanda_id + ")");
    }


    //Search functions
    public static Prajitura SearchCookieInDB(Connection conn, String den) throws SQLException {
        Prajitura praji = new Prajitura();
        pst = conn.prepareStatement("select denumire,stoc,pret,activ from prajitura where denumire = ?");
        pst.setString(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            String denumire = rs.getString(1);
            int stoc = rs.getInt(2);
            int pret = rs.getInt(3);
            boolean activ = rs.getBoolean(4);

            praji.setActive(activ);
            praji.setDenumire(denumire);
            praji.setPret(pret);
            praji.setStoc(stoc);
        }
        return praji;
    }

    public static Comanda SearchOrderInDB(Connection conn, int den) throws SQLException {
        Comanda comanda = new Comanda();
        pst = conn.prepareStatement("select nr_comanda,descriere,pret,activ from comanda where nr_comanda = ?");
        pst.setInt(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            int nr_comanda = rs.getInt(1);
            String descriere = rs.getString(2);
            int pret = rs.getInt(3);
            boolean activ = rs.getBoolean(4);

            comanda.setActive(activ);
            comanda.setProdus(descriere);
            comanda.setPret(pret);
            comanda.setNr_bucati(nr_comanda);
        }
        return comanda;
    }

    public static Tort SearchCakeInDB(Connection conn, String den) throws SQLException {
        Tort tort = new Tort();
        pst = conn.prepareStatement("select denumire,stoc,pret,activ from tort where denumire = ?");
        pst.setString(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            String denumire = rs.getString(1);
            int stoc = rs.getInt(2);
            int pret = rs.getInt(3);
            boolean activ = rs.getBoolean(4);

            tort.setActive(activ);
            tort.setDenumire(denumire);
            tort.setPret(pret);
            tort.setStoc(stoc);
        }
        return tort;
    }

    public static Client SearchClientInDB(Connection conn, String den) throws SQLException {
        Client client = new Client();
        pst = conn.prepareStatement("select nume,prenume,adresa,nr_telefon, id_comanda,activ from client where nume = ?");
        pst.setString(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            String nume = rs.getString(1);
            String prenume = rs.getString(2);
            String adresa = rs.getString(3);
            String nr_telefon = rs.getString(4);
            int an_inreg = rs.getInt(5);
            boolean activ = rs.getBoolean(6);

            client.setNume(nume);
            client.setPrenume(prenume);
            client.setAdresa(adresa);
            client.setNr_telefon(nr_telefon);
            client.setAn_inregistrare(an_inreg);
            client.setActive(activ);
        }
        return client;
    }

    public static Angajat SearchEmployeeInDB(Connection conn, String nume_dat) throws SQLException {
        Angajat angajat = new Angajat();
        pst = conn.prepareStatement("select nume,prenume,nr_telefon,salariu from angajat where nume = ?");
        pst.setString(1, (nume_dat));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            String nume = rs.getString(1);
            String prenume = rs.getString(2);
            String telefon = rs.getString(3);
            int salariu = rs.getInt(4);

            angajat.setNr_telefon(nume);
            angajat.setPrenume(prenume);
            angajat.setNr_telefon(telefon);
            angajat.setSalariu(salariu);
        }
        return angajat;
    }


    //Aux function
    public static int FindCookieIndex(Connection conn, String den) throws SQLException {
        int index_praji = 0;
        pst = conn.prepareStatement("select praji_id from prajitura where denumire = ?");
        pst.setString(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            index_praji = rs.getInt(1);
        }
        return index_praji;
    }

    public static int FindOrderIndex(Connection conn, String den) throws SQLException {
        int index_comanda = 0;
        pst = conn.prepareStatement("select comanda_id from comanda where descriere = ?");
        pst.setString(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            index_comanda = rs.getInt(1);
        }
        return index_comanda;
    }

    public static int FindCakeIndex(Connection conn, String den) throws SQLException {
        int index_tort = 0;
        pst = conn.prepareStatement("select tort_id from tort where denumire = ?");
        pst.setString(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            index_tort = rs.getInt(1);
        }
        return index_tort;
    }

    public static int FindClientIndex(Connection conn, String den) throws SQLException {
        int index_client = 0;
        pst = conn.prepareStatement("select client_id from client where nume = ?");
        pst.setString(1, (den));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            index_client = rs.getInt(1);
        }
        return index_client;
    }

    public static int FindEmployeeIndex(Connection conn, String nume_dat) throws SQLException {
        int index_angajat = 0;
        Angajat angajat = new Angajat();
        pst = conn.prepareStatement("select angajat_id from angajat where nume = ?");
        pst.setString(1, (nume_dat));
        ResultSet rs = null;
        rs = pst.executeQuery();

        if (rs.next()) {
            index_angajat = rs.getInt(1);
        }
        return index_angajat;
    }

}
