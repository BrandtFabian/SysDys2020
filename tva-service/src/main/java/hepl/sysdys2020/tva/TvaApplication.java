package hepl.sysdys2020.tva;

import hepl.sysdys2020.tva.Model.tvaItems;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class TvaApplication {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(TvaApplication.class, args);
    }


        @Bean
        public HashMap<String, Float> getTvaHashMap() {
            HashMap<String, Float> hashMap = new HashMap<>();
            hashMap.put("livres", 6f);
            hashMap.put("jeux", 15f);
            hashMap.put("nourriture", 3f);
            hashMap.put("divers", 21f);
            return hashMap;
        }

}
