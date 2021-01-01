package com.example.demo.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class stockItems {
    @Id
    private int idstock;
    private String fournisseur;
    private String libelle;
    private int quantite;
    private double prix;

    public stockItems() { }

    public stockItems(String fournisseur,String libelle, int quantite, double prix) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.fournisseur=fournisseur;
        this.prix = prix;
    }

    public stockItems(int idstock,String fournisseur, String libelle, int quantite, double prix) {
        this.idstock = idstock;
        this.libelle = libelle;
        this.fournisseur=fournisseur;
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

    public String getFournisseur() {
        return fournisseur;
    }
    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
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
                ", fournisseur='" + fournisseur +
                ", quantite=" + quantite +
                ", prix=" + prix +
                '}';
    }
}
