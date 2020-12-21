package hepl.sysdys2020.order.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.order.Model.OrderItems;
import hepl.sysdys2020.order.Model.OrderService;
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

    @RequestMapping("/updateprix/{id}/{prixtot}")
    public boolean setPrixTot(@PathVariable("id")Integer id, @PathVariable("prixtot")Integer prixtot){
        orderService.setPrixTot(id, prixtot);
        return true;
    }

    @RequestMapping("/updatestatus/{id}/{status}")
    public boolean setPrixTot(@PathVariable("id")Integer id, @PathVariable("status")String status){
        orderService.setStatus(id, status);
        return true;
    }

    @RequestMapping("/add/{idclient}/{prix}/{status}")
    public void addOrder(@PathVariable("idclient")Integer idclient, @PathVariable("prix") double prix, @PathVariable("status") String status){
        orderService.addOrder(idclient, prix, status);
    }

    @RequestMapping("/delete/{id}")
    public void deleteOrder(@PathVariable("id") Integer id){
        orderService.removeOrder(id);
    }

    @RequestMapping("/increaseprix/{id}/{prixtot}")
    public boolean IncreasePrixTot(@PathVariable("id")Integer id, @PathVariable("prixtot")Integer prixtot){
        System.out.println(id +":"+prixtot);
        orderService.setIncreasePrixTot(id, prixtot);
        return true;
    }

    @RequestMapping("/test")
    public String test(){
        return "Mastu****";
    }

}
