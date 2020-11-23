package hepl.sysdys2020.tva.Ressource;

import hepl.sysdys2020.tva.Model.tvaItems;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/tva")
public class tva {

    @RequestMapping("/{type}")
    public List<tvaItems> getTVA(@PathVariable("type") String type){
        return Collections.singletonList(
                new tvaItems("livres", 6)
        );
    }
}
