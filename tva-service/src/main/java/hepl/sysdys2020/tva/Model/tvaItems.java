package hepl.sysdys2020.tva.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class tvaItems {
    @Id
    @GeneratedValue
    private int idtva;

    private String libelle;
    private int pourcentage;

    public tvaItems() { }

    public tvaItems(String libelle, int pourcentage) {
        this.libelle = libelle;
        this.pourcentage = pourcentage;
    }

    public tvaItems(int idtva, String libelle, int pourcentage) {
        this.idtva = idtva;
        this.libelle = libelle;
        this.pourcentage = pourcentage;
    }

    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(int pourcentage) {
        this.pourcentage = pourcentage;
    }
}
