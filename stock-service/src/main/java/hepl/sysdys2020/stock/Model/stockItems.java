package hepl.sysdys2020.stock.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class stockItems {
    @Id
    @GeneratedValue
    private int idstock;

    private String libelle;
    private int quantite;

    public stockItems() { }

    public stockItems(String libelle, int quantite) {
        this.libelle = libelle;
        this.quantite = quantite;
    }

    public stockItems(int idstock, String libelle, int quantite) {
        this.idstock = idstock;
        this.libelle = libelle;
        this.quantite = quantite;
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

    @Override
    public String toString() {
        return "stockItems{" +
                "idstock=" + idstock +
                ", libelle='" + libelle + '\'' +
                ", quantite=" + quantite +
                '}';
    }
}
