package hepl.sysdys2020.server.Model.Model;

public class CarItemsShorter {
    private int idCart;
    private int idClient;
    private int idProduit;
    private int quantite;
    private boolean isVirtual;

    public CarItemsShorter(){}
    public CarItemsShorter( int idCart, int idClient, int idProduit, int quantite, boolean isVirtual){
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
                ", idCart=" + idCart +
                ", idClient=" + idClient +
                ", idProduit=" + idProduit +
                ", quantite=" + quantite +
                ", isVirtual=" + isVirtual +
                '}';
    }

}


