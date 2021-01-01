package hepl.sysdys2020.order.Model;

import hepl.sysdys2020.order.Ressource.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<OrderItems> getAllOrder(){
        List<OrderItems> orderItems = new ArrayList<>();
        orderRepository.findAll().forEach(orderItems::add);
        return orderItems;
    }

    public ListOrderItems getAllOrderByIdClient(int id){
        List<OrderItems> orderlistitem = new ArrayList<>();


        List<OrderItems> cart=orderRepository.findAll();

        for (OrderItems x: cart) {
            if(x.getIdClient()==id) {
                orderlistitem.add(
                        new OrderItems(x.getIdOrder(),x.getIdClient(),x.getStatus(),x.getPrixTotal()));};
        }
        ListOrderItems returnitems= new ListOrderItems();
        returnitems.setList(orderlistitem);


        return returnitems;
    }

    public OrderItems getOrderById(int id){
        OrderItems orderItems = orderRepository.findById(id).get();
        return orderItems;
    }

    public void setPrixTot(Integer id, Integer prixtot) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdborder", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "Update Order_Items Set Prix_Total = ? " +
                            "where id_Order = ?");
            statement.setInt(1, prixtot);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setStatus(Integer id, String status) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdborder", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "Update Order_Items Set Status = ? " +
                            "where id_Order = ?");
            statement.setString(1, status);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public OrderItems GetOrderByStatus(Integer id) {
        List<OrderItems> order=orderRepository.findAll();
        OrderItems orderitem = null;

        for (OrderItems x: order) {
            if(x.getIdClient()==id && x.getStatus().equals("Attente")) {

                       orderitem=x;
            };
        }
      return orderitem;
    }



    public int addOrder(int id, double prix, String status){


        int lastid=getLastId();
        OrderItems orderItems = new OrderItems(lastid,id, status, prix);
        orderRepository.save(orderItems);

            return lastid;

    }

    public void removeOrder(int id){
        orderRepository.deleteById(id);
    }

    public void setIncreasePrixTot(Integer id, Integer prixtot) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdborder", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "select * from Order_Items where Id_Order = ?");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                double prix = rs.getDouble("PRIX_TOTAL");
                prix += prixtot;
                statement = connection.prepareStatement(
                        "Update Order_Items Set Prix_Total = ? " +
                                "where id_Order = ?");
                statement.setDouble(1, prix);
                statement.setInt(2, id);
                statement.execute();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int getLastId(){
        int i=0;
        boolean test = true;
        while(test == true){
            i++;
            test = orderRepository.existsById(i);
        }
        return i;
    }

}
