public class Tort {
    String denumire;
    int stoc;
    int pret;
    Boolean active;
    int id;

    //Constructor
    public Tort() {
        this.id = -1;
        this.denumire = "";
        this.stoc = 0;
        this.active = false;
        this.pret = 0;
    }

    public Tort(int id, String denumire, int stoc, int pret, boolean active) {
        this.id = id;
        this.denumire = denumire;
        this.stoc = stoc;
        this.pret = pret;
        this.active = active;
    }

    public Tort(String denumire, int stoc, int pret) {
        this.denumire = denumire;
        this.stoc = stoc;
        this.pret = pret;
        this.active = true;
    }


    //Getters
    public int getId() {
        return id;
    }

    public String getDenumire() {
        return denumire;
    }

    public int getStoc() {
        return stoc;
    }

    public int getPret() {
        return pret;
    }


    public Boolean getActive() {
        return active;
    }



    //Setters
    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
