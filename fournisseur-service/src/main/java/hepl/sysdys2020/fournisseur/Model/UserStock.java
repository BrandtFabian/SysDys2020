package hepl.sysdys2020.fournisseur.Model;

import java.util.List;

public class UserStock {
    private List<Fournisseur> userStock;

    public List<Fournisseur> getUserStock() {
        return userStock;
    }

    public void setUserStock(List<Fournisseur> userStock) {
        this.userStock = userStock;
    }
}
