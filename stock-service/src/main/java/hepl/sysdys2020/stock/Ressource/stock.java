package hepl.sysdys2020.stock.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.stock.Model.UserStock;
import hepl.sysdys2020.stock.Model.stockItems;
import hepl.sysdys2020.stock.Model.stockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class stock {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    stockService stockService;

    private DiscoveryClient discoveryClient;

    public stock() { }

    @RequestMapping("/all")
    public UserStock getAllStock(){
        UserStock userStock = new UserStock();
        userStock.setUserStock(stockService.getAllStock());
        return userStock;
    }

    @RequestMapping("/{id}")
    public stockItems getStock(@PathVariable("id") Integer type){
        return stockService.getItemsbyId(type);
        }

    @RequestMapping("/delete/{id}")
    private void deleteStock(@PathVariable("id") Integer id) {
        stockService.delete(id);
    }

    @RequestMapping("/update/{id}/{quantite}")
    private int UpdateStock(@PathVariable("id") Integer id, @PathVariable("quantite") Integer quantite) {
        stockItems stockItems = stockService.getItemsbyId(id);
        stockItems.setQuantite(quantite);
        stockService.SaveOrUpdate(stockItems);
        return stockItems.getIdstock();
    }

    @RequestMapping("/add/{libelle}/{quantite}/{prix}")
    private int addStock(@PathVariable("libelle") String libelle,
                         @PathVariable("quantite") Integer quantite, @PathVariable("prix") double prix) {
        int id = stockService.getLastId();
        stockItems stockItems = new stockItems(id, libelle, quantite, prix);
        stockService.SaveOrUpdate(stockItems);
        return stockItems.getIdstock();
    }
}
