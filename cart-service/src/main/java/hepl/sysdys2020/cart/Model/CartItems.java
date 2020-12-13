package hepl.sysdys2020.cart.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CartItems {

    @Id
    @GeneratedValue
    private int idGeneral;
    private int idCart;
    private int idClient;
    private int idProduit;
    private int quantite;
    private boolean isVirtual;

    public CartItems(){}
    public CartItems(int idGeneral, int idCart, int idClient, int idProduit, int quantite, boolean isVirtual){
        this.idGeneral = idGeneral;
        this.idCart = idCart;
        this.idClient = idClient;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.isVirtual = isVirtual;
    }

    public CartItems(int idCart, int idClient, int idProduit, int quantite, boolean isVirtual){
        this.idCart = idCart;
        this.idClient = idClient;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.isVirtual = isVirtual;
    }

    public int getIdCart() {
        return idCart;
    }
    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdProduit() { return idProduit; }
    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public boolean isVirtual() { return isVirtual; }
    public void setVirtual(boolean virtual) { isVirtual = virtual; }

    @Override
    public String toString() {
        return "CartItems{" +
                "idGeneral=" + idGeneral +
                ", idCart=" + idCart +
                ", idClient=" + idClient +
                ", idProduit=" + idProduit +
                ", quantite=" + quantite +
                ", isVirtual=" + isVirtual +
                '}';
    }

}
