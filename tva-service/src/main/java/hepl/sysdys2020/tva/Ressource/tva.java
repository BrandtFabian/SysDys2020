package hepl.sysdys2020.tva.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.tva.Model.tvaItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.*;

@RestController
@RequestMapping("/tva")
public class tva {

    @Autowired
    private RestTemplate restTemplate;


    private DiscoveryClient discoveryClient;
    @Autowired
    private HashMap<String, Float> _listtva;

    public tva() { }

    @RequestMapping("/")
    public ArrayList<tvaItems> listtva(){
        Set<String> keys = _listtva.keySet();
        ArrayList<tvaItems> list = new ArrayList<tvaItems>();
        int i = 1;
        for (String key:keys)
        {
            list.add(new tvaItems(i, key, _listtva.get(key)));
            i++;
        }
        return list;
    }
    //ici return de plusieur tuple

    @RequestMapping("/{type}")
    public tvaItems getTVA(@PathVariable("type") String type){

        if(_listtva.containsKey(type))
            return new tvaItems(1, type, _listtva.get(type));
        else
            return new tvaItems(1, "Default", 21);
    }
}
