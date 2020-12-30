package hepl.sysdys2020.stock;

import hepl.sysdys2020.stock.Jms.CustomMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class StockApplication {

    private static ConfigurableApplicationContext privatecontext;

    @Bean
    @LoadBalanced

    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public static ConfigurableApplicationContext Getcontext(){ return  privatecontext; }
    public static void setContext(ConfigurableApplicationContext c){ privatecontext=c; }

    public static void main(String[] args) {
        ConfigurableApplicationContext context= SpringApplication.run(StockApplication.class,args);
        setContext(context);

    }

}
    /*
    @Bean
    public HashMap<String, Integer> getTvaHashMap() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("livres", 25);
        hashMap.put("jeux", 10);
        hashMap.put("nourriture", 150);
        hashMap.put("divers", 30);
        return hashMap;
    }
    */
