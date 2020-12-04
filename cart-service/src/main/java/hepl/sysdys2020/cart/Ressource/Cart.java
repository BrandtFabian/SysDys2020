package hepl.sysdys2020.cart.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.cart.Model.CartItems;
import hepl.sysdys2020.cart.Model.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class Cart {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CartService cartService;

    private DiscoveryClient discoveryClient;

    public Cart(){}

    @RequestMapping("/allcart")
    public List<CartItems> getAllCart(){
        return cartService.getAllCart();
    }

    @RequestMapping("/{id}")
    public List<CartItems> getCart(@PathVariable("id") Integer id){
        return cartService.getCartbyId(id);
    }

    @RequestMapping("/delete/{id}")
    public void deleteCart(@PathVariable("CartItems") Integer id){
        cartService.delete(id);
    }

    @PostMapping("cart")
    private int saveCart(@PathVariable("CartItems") CartItems cartItems){
        cartService.SaveOrUpdate(cartItems);
        return cartItems.getIdCart();
    }
}
