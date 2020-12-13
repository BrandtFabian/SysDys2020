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

    public void addOrder(int id, double prix, String status){
        OrderItems orderItems = new OrderItems(id, status, prix);
            orderRepository.save(orderItems);
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

}
