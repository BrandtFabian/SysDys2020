package hepl.sysdys2020.checkout.Ressource;

import hepl.sysdys2020.checkout.Model.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/checkout")
public class CheckOut {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder builder;

    @RequestMapping("/fin/{id}/{type}")
    public void fincommande(@PathVariable("id")Integer id, @PathVariable("type")String type){
        //todo rajouter dans le prix total selon le type
        int prix = 5;
        if(type.equals("EXPRESS"))
            prix = 10;
        boolean bool = builder.build().get().uri("http://order-service/order/increaseprix/"+id+"/"+prix).retrieve().bodyToMono(boolean.class).block();
        //todo changé la commande de en prepa en livraison
        builder.build().get().uri("http://order-service/order/updatestatus/"+id+"/EXPEDIEE").retrieve().bodyToMono(boolean.class).block();

    }
}
