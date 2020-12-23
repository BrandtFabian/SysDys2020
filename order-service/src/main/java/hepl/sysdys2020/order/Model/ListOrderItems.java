package hepl.sysdys2020.order.Model;

import java.util.ArrayList;
import java.util.List;

public class ListOrderItems {
    private List<OrderItems> list;

    public ListOrderItems() {
        this.list = new ArrayList<OrderItems>();
    }

    public List<OrderItems> getList() {
        return list;
    }

    public void setList(List<OrderItems> list) {
        this.list = list;
    }

    public ListOrderItems(List<OrderItems> list) {
        this.list = list;
    }
}
