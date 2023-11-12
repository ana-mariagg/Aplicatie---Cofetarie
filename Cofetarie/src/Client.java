public class Client {
    String nume;
    String prenume;
    String nr_telefon;
    String adresa;
    int id;
    int an_inregistrare;
    boolean active;

    //Constructor
    public Client() {
        this.id = -1;
        this.nume = "";
        this.prenume = "";
        this.nr_telefon = "";
        this.adresa = "";
        this.an_inregistrare = -1;
        this.active = false;
    }

    public Client(int id, String nume, String prenume, String nr_telefon, String adresa, int an_inregistrare, boolean active) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.nr_telefon = nr_telefon;
        this.adresa = adresa;
        this.an_inregistrare = an_inregistrare;
        this.active = active;
    }

    public Client(String nume, String prenume, String nr_telefon, String adresa, int an_inregistrare) {
        this.nume = nume;
        this.prenume = prenume;
        this.nr_telefon = nr_telefon;
        this.adresa = adresa;
        this.an_inregistrare = an_inregistrare;
        this.active = true;
    }


    //Getters
    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNr_telefon() {
        return nr_telefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public int getId() {
        return id;
    }

    public int getAn_inregistrare() {
        return an_inregistrare;
    }

    public Boolean getActive() {
        return active;
    }


    //Setters
    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setNr_telefon(String nr_telefon) {
        this.nr_telefon = nr_telefon;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAn_inregistrare(int an_inregistrare) {
        this.an_inregistrare = an_inregistrare;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
