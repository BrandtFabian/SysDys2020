package hepl.sysdys2020.tva.Ressource;

import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.tva.Model.tvaItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tva")
public class tva {

    @Autowired
    private RestTemplate restTemplate;


    private DiscoveryClient discoveryClient;

    public tva() {
    }
    //ici return de plusieur tuple

    @RequestMapping("/{type}")
    public List<tvaItems> getTVA(@PathVariable("type") String type){


        //ici dois avoir acces a la bdd et return un tuple
        return Collections.singletonList(
                new tvaItems("livres", 6)
        );
    }
}
