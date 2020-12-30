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

    @RequestMapping("/allcarttrue")
    public List<Integer> getAllCarttrue(){
        return cartService.getAllCartTrue();
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
    @RequestMapping("/changeidclient/{id}/{idclient}")// id du cart a rechecher // idclient pour remplacer
    public boolean UpDate(@PathVariable("id") Integer id,@PathVariable("idclient") Integer idclient){
        return cartService.UpdateCart(id,idclient);
    }

    @RequestMapping("/deletecart/{id}")
    public boolean DeleteCart(@PathVariable("id") Integer id){
        return cartService.delete(id);
    }

    @PostMapping("/cart")
    private int saveCart(@PathVariable("CartItems") CartItems cartItems){
        cartService.SaveOrUpdate(cartItems);
        return cartItems.getIdCart();
    }

    @RequestMapping("/add/{idcart}/{idclient}/{idproduit}/{quantite}")
    private boolean addCart(@PathVariable("idcart") Integer idCart, @PathVariable("idclient") Integer idClient,
                            @PathVariable("idproduit") Integer idproduit, @PathVariable("quantite") Integer quantite){
        if(idCart==0)
        {
            return false;
        }
        CartItems cartItems = cartService.getCart(idCart, idClient, idproduit);
        //s'il existe MAJ
        if(cartItems != null){
            cartItems.setQuantite(cartItems.getQuantite()+quantite);
            cartService.SaveOrUpdate(cartItems);
        }//s'il existe pas, on le crée
        else {
            int idGeneral = cartService.getLastId();
            CartItems newcartItems = new CartItems(idGeneral, idCart, idClient, idproduit, quantite, true);
            cartService.SaveOrUpdate(newcartItems);
        }
        return true;
    }

    @PostMapping("/add")
    private boolean addCart(@RequestBody CartItems carti ){

        CartItems cartItems = cartService.getCart(carti.getIdCart(), carti.getIdClient(), carti.getIdProduit());
        //s'il existe MAJ
        if(cartItems != null){
            cartItems.setQuantite(carti.getQuantite());
            cartService.SaveOrUpdate(cartItems);
        }//s'il existe pas, on le crée
        else {
            int idGeneral = cartService.getLastId();
            CartItems newcartItems = new CartItems(idGeneral, carti.getIdCart(), carti.getIdClient(), carti.getIdProduit(), carti.getQuantite(), carti.isVirtual());
            cartService.SaveOrUpdate(newcartItems);
        }
        return true;
    }

    @RequestMapping("/delete/{idcart}/{idclient}/{idproduit}")
    private boolean deleteCart(@PathVariable("idcart") Integer idCart, @PathVariable("idclient") Integer idClient,
                         @PathVariable("idproduit") Integer idproduit){
        boolean i = cartService.deleteCartById(idCart, idClient, idproduit);
        System.out.println(i);
        return i;
    }

    @RequestMapping("/create/cart/{id}")
    private int createCart(@PathVariable("id") Integer idclient)
    {

        int i=cartService.GetNewCart(idclient);
        return i;
    }
}
