package hepl.sysdys2020.stock.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class stockItems {
    @Id
    private int idstock;
    private String libelle;
    private int quantite;
    private double prix;

    public stockItems() { }

    public stockItems(String libelle, int quantite, double prix) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.prix = prix;
    }

    public stockItems(int idstock, String libelle, int quantite, double prix) {
        this.idstock = idstock;
        this.libelle = libelle;
        this.quantite = quantite;
        this.prix = prix;
    }

    public int getIdstock() { return idstock; }
    public void setIdstock(int idstock) { this.idstock = idstock; }

    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "stockItems{" +
                "idstock=" + idstock +
                ", libelle='" + libelle + '\'' +
                ", quantite=" + quantite +
                ", prix=" + prix +
                '}';
    }
}
