package hepl.sysdys2020.ordersservice.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
