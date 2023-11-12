
public class Comanda {
    int nr_bucati;
    String produs;
    int pret;
    int id;
    boolean active;

    //Constructor
    public Comanda() {
        this.id = -1;
        this.nr_bucati = -1;
        this.produs = "";
        this.pret = -1;
        this.active = false;

    }

    public Comanda(int id, int nr_bucati, String produs, int pret, boolean active) {
        this.id = id;
        this.nr_bucati = nr_bucati;
        this.produs = produs;
        this.pret = pret;
        this.active = active;

    }

    public Comanda(int nr_bucati, String produs, int pret) {
        this.nr_bucati = nr_bucati;
        this.produs = produs;
        this.pret = pret;
        this.active = true;
    }


    //Getters
    public int getNr_bucati() {
        return nr_bucati;
    }

    public String getProdus() {
        return produs;
    }

    public int getPret() {
        return pret;
    }

    public int getId() {
        return id;
    }

    public Boolean getActive() {
        return active;
    }


    //Setters
    public void setNr_bucati(int nr_bucati) {
        this.nr_bucati = nr_bucati;
    }

    public void setProdus(String produs) {
        this.produs = produs;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
