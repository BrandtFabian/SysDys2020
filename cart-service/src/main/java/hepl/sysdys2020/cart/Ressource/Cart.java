package hepl.sysdys2020.cart.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.cart.Model.CartItems;
import hepl.sysdys2020.cart.Model.CartService;
import hepl.sysdys2020.cart.Model.ListCartItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
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
    public ListCartItems getCart(@PathVariable("id") Integer id){
        return cartService.getCartbyId(id);
    }

    @RequestMapping("/{idclient}/{idcart}")
    public ListCartItems getCartbyclient(@PathVariable("idclient") Integer idclient, @PathVariable("idcart") Integer idcart){
        return cartService.getCartbyIdClientAndIdCart(idclient, idcart);
    }

    @RequestMapping("/delete/{id}")
    public void deleteCart(@PathVariable("CartItems") Integer id){
        cartService.delete(id);
    }

    @PostMapping("/cart")
    private int saveCart(@PathVariable("CartItems") CartItems cartItems){
        cartService.SaveOrUpdate(cartItems);
        return cartItems.getIdCart();
    }

    @RequestMapping("/add/{idcart}/{idclient}/{idproduit}/{quantite}")
    private void addCart(@PathVariable("idcart") Integer idCart, @PathVariable("idclient") Integer idClient,
                        @PathVariable("idproduit") Integer idproduit, @PathVariable("quantite") Integer quantite){
        CartItems cartItems = cartService.getCart(idCart, idClient, idproduit);
        //s'il existe MAJ
        if(cartItems != null){
            cartItems.setQuantite(quantite);
            cartService.SaveOrUpdate(cartItems);
        }//s'il existe pas, on le crée
        else {
            int idGeneral = cartService.getLastId();
            CartItems newcartItems = new CartItems(idGeneral, idCart, idClient, idproduit, quantite, true);
            cartService.SaveOrUpdate(newcartItems);
        }
    }

    @RequestMapping("/delete/{idcart}/{idclient}/{idproduit}")
    private boolean deleteCart(@PathVariable("idcart") Integer idCart, @PathVariable("idclient") Integer idClient,
                         @PathVariable("idproduit") Integer idproduit){
        boolean i = cartService.deleteCartById(idCart, idClient, idproduit);
        System.out.println(i);
        return i;
    }
}
