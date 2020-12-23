package hepl.sysdys2020.server.Model.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.server.Model.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("server")
public class server {
    @Autowired
    private RestTemplate restTemplate;

    private DiscoveryClient discoveryClient;

    
    @Autowired
    WebClient.Builder builder;

    public server() { }

    @RequestMapping("/stock/all")
    public UserStock getStockAll(){
        UserStock userStock = builder.build()
                .get()
                .uri("http://stock-service/stock/all")
                .retrieve()
                .bodyToMono(UserStock.class)
                .block();
        return userStock;
    }


    @RequestMapping("/stock/{id}")//get produit by id
    public stockItems getStockById(@PathVariable ("id") Integer id){
        stockItems stockItems = builder.build()
                .get()
                .uri("http://stock-service/stock/"+id)
                .retrieve()
                .bodyToMono(stockItems.class)
                .block();
        return stockItems;
    }

    @RequestMapping("/prix/{id}")//get prix by id du stock
    public double getPrixById(@PathVariable("id") Integer id) {
        stockItems stockItems = builder.build()
                .get()
                .uri("http://stock-service/stock/"+id)
                .retrieve()
                .bodyToMono(stockItems.class)
                .block();

        tvaItems tva = builder.build()
                .get()
                .uri("http://tva-service/tva/"+stockItems.getLibelle())
                .retrieve()
                .bodyToMono(tvaItems.class)
                .block();

        double prix = stockItems.getPrix() +  (stockItems.getPrix() * tva.getPourcentage() / 100);
        return prix;
    }
    @RequestMapping("/user/panier/{id}")//get panier for user
    public int GetPanierForAnUser(@PathVariable("id") Integer id) {
        ListCartItems listcartItems = builder.build()
                .get()
                .uri("http://cart-service/cart/"+id)
                .retrieve()
                .bodyToMono(ListCartItems.class)
                .block();
        for (CartItems list:listcartItems.getList()) {
            //recupere un item du stock
                if(list.getIdClient()==id)
                {
                    return list.getIdCart();
                }

        }

        return 0;
    }

    @RequestMapping("get/panier/{id}")//afficher panier par id client
    public ListCartItems GetCartItems(@PathVariable("id") Integer id) {

          ListCartItems listcartItems = builder.build()
                .get()
                .uri("http://cart-service/cart/" + id)
                .retrieve()
                .bodyToMono(ListCartItems.class)
                .block();

        return listcartItems;
    }

    @RequestMapping("/panier/{id}")//afficher panier par id client
    public PanierList getPanier(@PathVariable("id") Integer id){

        ListCartItems listcartItems = builder.build()
                .get()
                .uri("http://cart-service/cart/"+id)
                .retrieve()
                .bodyToMono(ListCartItems.class)
                .block();

        List<Panier> ListPanier = new ArrayList<>();
        double prixtotal = 0;
        for (CartItems list:listcartItems.getList()) {
            //recupere un item du stock
            stockItems stockItems = builder.build()
                    .get()
                    .uri("http://stock-service/stock/"+list.getIdProduit())
                    .retrieve()
                    .bodyToMono(stockItems.class)
                    .block();

            tvaItems tva = builder.build()
                    .get()
                    .uri("http://tva-service/tva/"+stockItems.getLibelle())
                    .retrieve()
                    .bodyToMono(tvaItems.class)
                    .block();
            double prix = stockItems.getPrix() +  (stockItems.getPrix() * tva.getPourcentage() / 100);
            prixtotal += prix*list.getQuantite();
            Panier panier = new Panier(stockItems.getLibelle(), list.getQuantite(), prix, prix*list.getQuantite());
            ListPanier.add(panier);
        }
        PanierList panierList = new PanierList();
        panierList.setList(ListPanier);
        panierList.setPrixtotal(prixtotal);
        return panierList;
    }

    @RequestMapping("/cart/add/{idcart}/{idclient}/{idproduit}/{quantite}")
    public void addcart(@PathVariable("idcart") Integer idCart, @PathVariable("idclient") Integer idClient,
                        @PathVariable("idproduit") Integer idProduit, @PathVariable("quantite") Integer quantite){
        builder.build()
                .get()
                .uri("http://cart-service/cart/add/"+idCart+"/"+idClient+"/"+idProduit+"/"+quantite)
                .retrieve()
                .bodyToMono(boolean.class)
                .block();
    }

// add with resquestbody
    @PostMapping("/cart/add")
    public void AddItemInCart(@RequestBody CarItemsShorter c){


        builder.build()
                .get()
                .uri("http://cart-service/cart/add")
                .retrieve()
                .bodyToMono(boolean.class)
                .block();
    }

    @RequestMapping("/order/{id}")
    public OrderStock getOrder(@PathVariable("id") Integer id){
        OrderItems orderItems = builder.build()
                .get()
                .uri("http://order-service/order/"+id)
                .retrieve()
                .bodyToMono(OrderItems.class)
                .block();

        ListCartItems listcartItems = builder.build()
                .get()
                .uri("http://cart-service/cart/"+orderItems.getIdClient()+"/"+orderItems.getIdOrder())
                .retrieve()
                .bodyToMono(ListCartItems.class)
                .block();

        List<stockItems> stockItemsList = new ArrayList<>();
        double prix = 0;
        for (CartItems list:listcartItems.getList()) {
            //recupere un item du stock
            stockItems stockItems = builder.build()
                    .get()
                    .uri("http://stock-service/stock/"+list.getIdProduit())
                    .retrieve()
                    .bodyToMono(stockItems.class)
                    .block();
            prix += stockItems.getQuantite() * getPrixById(stockItems.getIdstock());
            stockItems.setQuantite(list.getQuantite());
            stockItemsList.add(stockItems);
        }
        OrderStock orderStock = new OrderStock();
        orderStock.setStockItemsList(stockItemsList);
        orderItems.setPrixTotal(prix);
        orderStock.setOrderItems(orderItems);

        return orderStock;
    }

    @RequestMapping("/delete/cart/{idcart}/{idclient}/{idproduit}")
    public boolean DeleteItemFromACart(@PathVariable("idcart") Integer idCart, @PathVariable("idclient") Integer idClient,
                                         @PathVariable("idproduit") Integer idProduit){
        boolean ok = builder.build()
                .get()
                .uri("http://cart-service/cart/delete/"+idCart+"/"+idClient+"/"+idProduit)
                .retrieve()
                .bodyToMono(boolean.class)
                .block();
        return true;
    }
    @RequestMapping("/create/cart/{idclient}")
    public int CreateCartForUser(@PathVariable("idclient") Integer idClient){
        int ok = builder.build()
                .get()
                .uri("http://cart-service/cart/create/cart/"+idClient)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        return ok;
    }

    @RequestMapping("/create/order/{idclient}/{prix}/{status}")
    public int CreateOrderForUser(@PathVariable("idclient")Integer idclient, @PathVariable("prix") double prix, @PathVariable("status") String status){
        int ok = builder.build()
                .get()
                .uri("http://order-service/order/add/"+idclient+"/"+prix+"/"+status)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();

        return ok;
    }

    @RequestMapping("/delivery/amount/{id}/{type}")
    public boolean ChangeOrderForUser(@PathVariable("id")Integer id, @PathVariable("type") String type){
        boolean ok = builder.build()
                .get()
                .uri("http://checkout-service/checkout/attente/"+id+"/"+type)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return ok;
    }

    @RequestMapping("/finishorder/{id}")
    public boolean ChangeOrderForUser(@PathVariable("id")Integer id){
        boolean ok = builder.build()
                .get()
                .uri("http://checkout-service/checkout/fin/"+id)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return ok;
    }
    @RequestMapping("/order/prix/{id}")
    public double GetOrderByid(@PathVariable("id") Integer id) {
        OrderItems orderItems = builder.build()
                .get()
                .uri("http://order-service/order/" + id)
                .retrieve()
                .bodyToMono(OrderItems.class)
                .block();
        return orderItems.getPrixTotal();
    }

    @RequestMapping("/get/order/{idclient}")
    public  ListOrderItems GetAllOrderByid(@PathVariable("idclient")Integer idclient) {
         ListOrderItems orderItems = builder.build()
                .get()
                .uri("http://order-service/order/user/" + idclient)
                .retrieve()
                .bodyToMono(ListOrderItems.class)
                .block();
        return orderItems;
    }


}
