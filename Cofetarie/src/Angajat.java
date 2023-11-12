public class Angajat {
    String nume;
    String prenume;
    String nr_telefon;
    int salariu;
    int id;

    //Constructor
    public Angajat() {
        this.id = -1;
        this.nume = "";
        this.prenume = "";
        this.nr_telefon = "";
        this.salariu = 0;

    }

    public Angajat(int id, String nume, String prenume, String nr_telefon, int salariu) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.nr_telefon = nr_telefon;
        this.salariu = salariu;

    }

    public Angajat(String nume, String prenume, String nr_telefon, int salariu) {

        this.nume = nume;
        this.prenume = prenume;
        this.nr_telefon = nr_telefon;
        this.salariu = salariu;

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

    public int getSalariu() {
        return salariu;
    }

    public int getId() {
        return id;
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

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public void setId(int id) {
        this.id = id;
    }
}
