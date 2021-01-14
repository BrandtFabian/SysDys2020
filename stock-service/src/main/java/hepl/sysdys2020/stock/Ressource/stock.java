package hepl.sysdys2020.stock.Ressource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.stock.Jms.CustomMessage;
import hepl.sysdys2020.stock.Jms.Receiver;
import hepl.sysdys2020.stock.Model.UserStock;
import hepl.sysdys2020.stock.Model.stockItems;
import hepl.sysdys2020.stock.Model.stockRepository;
import hepl.sysdys2020.stock.Model.stockService;
import hepl.sysdys2020.stock.StockApplication;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.jms.Queue;
import java.util.*;

@RestController
@Component
@RequestMapping("/stock")
@EnableJms
public class stock {

    private static Logger log = LoggerFactory.getLogger(Receiver.class);
    private static StockApplication sta;
    private static final Logger logger = LoggerFactory.getLogger(stock.class);
    private static String newquantity="";
    private static boolean Onchange=false;
    private static boolean OnchangeMessage=false;
    private static stockItems items=null;
    private static String fournisseurname="";
    private static String libelleOrder="";
    private static String messageIn="";
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    stockService stockService;

    @Autowired
    private Queue queue;

    @Autowired
    stockRepository stockRepository;

    @Value("${jsa.activemq.topic}")
    String topic;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    WebClient.Builder builder;

    private DiscoveryClient discoveryClient;

    public stock() { }


    //--------------------------------------------------------------------------------------------------------------
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

        stockItems.setQuantite(stockItems.getQuantite()+quantite);
        stockService.SaveOrUpdate(stockItems);
        return stockItems.getIdstock();
    }
    @RequestMapping("/updateminus/{id}/{quantite}")
    private int UpdateStockminus(@PathVariable("id") Integer id, @PathVariable("quantite") Integer quantite) {
        stockItems stockItems = stockService.getItemsbyId(id);
        stockItems.setQuantite(stockItems.getQuantite()-quantite);
        stockService.SaveOrUpdate(stockItems);
        return stockItems.getIdstock();
    }

    /*
    @JmsListener(destination = "myqueue", containerFactory = "jmsContainerFactory", selector = "priority = 'high'")
    public void receiveMessage(CustomMessage message, @Headers Map<String, Object> headers)
        {
            System.out.println("Received <" + message + ">");
            log.info("priority=" + String.valueOf(headers.get("priority")));
        }
        */



    @PostMapping("/message")
    public void sendMessage(@RequestBody String student) {

        // test
        //List<String>quantite= Arrays.asList("demande/jeux".split("/"));
       List<String>quantite= Arrays.asList(student.split("/"));
        libelleOrder= quantite.get(1);

        try {
            ObjectMapper mapper = new ObjectMapper();

            //jmsTemplate.convertAndSend(new ActiveMQTopic(topic), "f/demande/jeux");
            jmsTemplate.convertAndSend(new ActiveMQTopic(topic), "f/"+student);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    @JmsListener(destination = "${jsa.activemq.topic}")
    public void consumeMessage(String message) {
        List<String> quantite = Arrays.asList(message.split("/"));

        
        if (message.equals("rien") == true & OnchangeMessage==false) {
            messageIn=message;
            OnchangeMessage=true;
            Onchange = true;
        }
        else
        {
           if( message.equals("rien") == true & messageIn.equals("rien")==true)
            {

            }

        }

        /// be able to don't check his own message.
        if(quantite.size()>4)
        {
            if (message.equals("rien") == false) {
                logger.info("Message received from activemq queue---" + message);


                if (Onchange == false) {


                        fournisseurname = quantite.get(0);
                        items = new stockItems(Integer.parseInt(quantite.get(1)), "", Integer.parseInt(quantite.get(2)), Double.parseDouble(quantite.get(3)));
                        Onchange = true;
                        newquantity = message;


                } else {
                    Onchange = false;
                    double prix = Double.parseDouble(quantite.get(3));

                    if(OnchangeMessage==true)
                    {
                        if(prix!=0)
                        {
                            fournisseurname = quantite.get(0);
                            items.setIdstock(Integer.parseInt(quantite.get(1)));
                            items.setPrix(prix);
                            items.setQuantite(Integer.parseInt(quantite.get(2)));
                            newquantity = message;
                        }

                    }
                    else
                    {
                        if (items.getPrix() > prix) {
                            fournisseurname = quantite.get(0);
                            items.setPrix(prix);
                            items.setQuantite(Integer.parseInt(quantite.get(2)));
                            newquantity = message;
                        }
                    }


                    String reponse = "f/validation" + "/" + fournisseurname + "/" + libelleOrder;
                    jmsTemplate.convertAndSend(new ActiveMQTopic(topic), reponse);

                    //
                    logger.info("Message received from activemq queue---" + reponse);

                    //
                    stockItems stockItems = stockService.getItemsbyId(items.getIdstock());
                    stockItems.setQuantite(items.getQuantite());
                    stockItems.setPrix(items.getPrix());
                    //stockService.SaveOrUpdate(stockItems)
                    stockRepository.save(stockItems);
                    OnchangeMessage=false;
                }


            }
        }


    }

    @RequestMapping("/getnewquantity")
    public String getNewquantity()
    {
      return  newquantity;
    }

/*
    @RequestMapping("/send/{id}")
    private void UpdateStock(@PathVariable("id") int id) {
        JmsTemplate jmsTemplate = sta.Getcontext().getBean(JmsTemplate.class);
        System.out.println("Sending a message.");
        jmsTemplate.convertAndSend("myqueue", new CustomMessage("info@example.com", id), messagePostProcessor -> {
            messagePostProcessor.setStringProperty("priority",
                    "high");
            return messagePostProcessor;
        });
    }

 */


    @RequestMapping("/add/{libelle}/{quantite}/{prix}")
    private int addStock(@PathVariable("libelle") String libelle,
                         @PathVariable("quantite") Integer quantite, @PathVariable("prix") double prix) {
        int id = stockService.getLastId();
        stockItems stockItems = new stockItems(id, libelle, quantite, prix);
        stockService.SaveOrUpdate(stockItems);
        return stockItems.getIdstock();
    }



}
