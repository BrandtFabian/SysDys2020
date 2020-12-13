package hepl.sysdys2020.server.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class ListCartItems {
    private List<CartItems> list;

    public ListCartItems() {
        this.list = new ArrayList<CartItems>();
    }

    public List<CartItems> getList() {
        return list;
    }

    public void setList(List<CartItems> list) {
        this.list = list;
    }

    public ListCartItems(List<CartItems> list) {
        this.list = list;
    }
}
