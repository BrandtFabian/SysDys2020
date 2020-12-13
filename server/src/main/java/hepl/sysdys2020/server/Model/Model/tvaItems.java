package hepl.sysdys2020.server.Model.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class tvaItems {
    private int idtva;

    private String libelle;
    private float pourcentage;

    public tvaItems() { }

    public tvaItems(String libelle, float pourcentage) {
        this.libelle = libelle;
        this.pourcentage = pourcentage;
    }

    public tvaItems(int idtva, String libelle, float pourcentage) {
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

    public float getPourcentage() {
        return pourcentage;
    }
    public void setPourcentage(float pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public String toString() {
        return "tvaItems{" +
                "idtva=" + idtva +
                ", libelle='" + libelle + '\'' +
                ", pourcentage=" + pourcentage +
                '}';
    }
}
