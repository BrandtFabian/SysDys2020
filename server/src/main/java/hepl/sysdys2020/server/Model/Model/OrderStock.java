package hepl.sysdys2020.server.Model.Model;

import java.util.List;

public class OrderStock {
    private OrderItems orderItems;
    private List<stockItems> stockItemsList;

    public OrderStock() {
    }

    public OrderStock(OrderItems orderItems, List<stockItems> stockItemsList) {
        this.orderItems = orderItems;
        this.stockItemsList = stockItemsList;
    }

    public OrderItems getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItems orderItems) {
        this.orderItems = orderItems;
    }

    public List<stockItems> getStockItemsList() {
        return stockItemsList;
    }

    public void setStockItemsList(List<stockItems> stockItemsList) {
        this.stockItemsList = stockItemsList;
    }
}
