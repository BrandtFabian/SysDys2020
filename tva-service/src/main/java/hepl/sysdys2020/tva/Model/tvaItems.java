package hepl.sysdys2020.tva.Model;

public class tvaItems {
    private String type;
    private int pourcentage;

    public tvaItems() {
    }

    public tvaItems(String type, int pourcentage) {
        this.type = type;
        this.pourcentage = pourcentage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }
}
