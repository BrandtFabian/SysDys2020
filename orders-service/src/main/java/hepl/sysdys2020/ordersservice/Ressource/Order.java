package hepl.sysdys2020.ordersservice.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.ordersservice.Model.OrderItems;
import hepl.sysdys2020.ordersservice.Model.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class Order {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    OrderService orderService;

    private DiscoveryClient discoveryClient;

    public Order() { }

    @RequestMapping("/all")
    public List<OrderItems> getAllOrder(){
        List<OrderItems> orderItemsList = orderService.getAllOrder();
        return orderItemsList;
    }

    @RequestMapping("/{id}")
    public OrderItems getOrderById(@PathVariable("id") Integer id){
        OrderItems orderItems = orderService.getOrderById(id);
        return orderItems;
    }

}
