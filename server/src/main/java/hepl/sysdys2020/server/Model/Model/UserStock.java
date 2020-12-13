package hepl.sysdys2020.server.Model.Model;

import java.util.List;

public class UserStock {
    private List<stockItems> userStock;

    public List<stockItems> getUserStock() {
        return userStock;
    }

    public void setUserStock(List<stockItems> userStock) {
        this.userStock = userStock;
    }

    public void addUserStock(stockItems stockItems){
        userStock.add(stockItems);
    }
}
