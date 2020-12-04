package hepl.sysdys2020.stock.Ressource;

import com.netflix.discovery.DiscoveryClient;
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
    @Autowired
    private HashMap<String, Integer> _liststock;

    public stock() { }

    @RequestMapping("/AllStock")
    public List<stockItems> getAllStock(){

        return stockService.getAllStock();
    }

    @RequestMapping("/{id}")
    public stockItems getStock(@PathVariable("id") Integer type){
        return stockService.getItemsbyId(type);
        }

    @RequestMapping("/delete/{id}")
    private void deletePerson(@PathVariable("id") Integer id) {
        stockService.delete(id);
    }

    @PostMapping("/persons")
    private int savePerson(@RequestBody stockItems stockItems) {
        stockService.SaveOrUpdate(stockItems);
        return stockItems.getIdstock();
    }
}

/*
    @DeleteMapping("/{id}")
    private void deletePerson(@PathVariable("id") int id) {
        stockService.delete(id);
    }

    @RequestMapping("/SOU/{id}/{libelle}/{quantite}")
    stockItems si = new stockItems(@PathVariable("id") int id, @PathVariable("libelle") string libelle, @PathVariable("quantite") int quantite)
    stockService.SaveOrUpdate(si);
    return si.getIdstock();
    }



    @RequestMapping("/")
    public ArrayList<stockItems> listtva(){
        Set<String> keys = _liststock.keySet();
        ArrayList<stockItems> list = new ArrayList<stockItems>();
        int i = 1;
        for (String key:keys)
        {
            list.add(new stockItems(i, key, _liststock.get(key)));
            i++;
        }
        return list;
    }

    @RequestMapping("/{type}")
    public stockItems getStock(@PathVariable("type") String type){

        if(_liststock.containsKey(type))
            return new stockItems(1, type, _liststock.get(type));
        else
            return new stockItems(1, "Default", 0);
    }

 */