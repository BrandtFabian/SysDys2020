package hepl.sysdys2020.stock.Model;

import java.util.List;

public class UserStock {
    private List<stockItems> userStock;

    public List<stockItems> getUserStock() {
        return userStock;
    }

    public void setUserStock(List<stockItems> userStock) {
        this.userStock = userStock;
    }
}
