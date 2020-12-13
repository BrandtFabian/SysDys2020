package hepl.sysdys2020.server.Model.Model;

public class Panier {
    private String libelle;
    private int quantite;
    private double prixunitaire;
    private double prixtotal;

    public Panier(String libelle, int quantite, double prixunitaire, double prixtotal) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.prixunitaire = prixunitaire;
        this.prixtotal = prixtotal;
    }

    public double getPrixtotal() {
        return prixtotal;
    }

    public void setPrixtotal(double prixtotal) {
        this.prixtotal = prixtotal;
    }

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

    public double getPrixunitaire() {
        return prixunitaire;
    }

    public void setPrixunitaire(double prixunitaire) {
        this.prixunitaire = prixunitaire;
    }
}
