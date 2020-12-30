package hepl.sysdys2020.fournisseur.Ressurce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.DiscoveryClient;
import hepl.sysdys2020.fournisseur.Jms.CustomMessage;
import hepl.sysdys2020.fournisseur.Model.stockItems;
import hepl.sysdys2020.fournisseur.Model.stockService;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.jms.Queue;
import java.util.Arrays;
import java.util.List;


@RestController
@Component
@RequestMapping("/fournisseur")
@EnableJms
public class Fournisseur {

    //private static Logger log = LoggerFactory.getLogger(Receiver.class);
    //private static StockApplication sta;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    stockService stockService;

    /*
    @Autowired
    private Queue queue;

     */
    // use topic beacuse cannot be destroy before everyone ahve read this message
    @Value("${jsa.activemq.topic}")
    String topic;




    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    WebClient.Builder builder;

    private static final Logger logger = LoggerFactory.getLogger(Fournisseur.class);
    private DiscoveryClient discoveryClient;

    @PostMapping("/message")
    public CustomMessage sendMessage(@RequestBody CustomMessage student) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String studentAsJson = mapper.writeValueAsString(student);

            jmsTemplate.convertAndSend(topic, studentAsJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    /*
    //@RequestMapping("/message")
    public CustomMessage sendMessage( String quantityreturn) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            String studentAsJson = mapper.writeValueAsString(student);

            jmsTemplate.convertAndSend(queue, studentAsJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
*/

    @JmsListener(destination = "${jsa.activemq.topic}",containerFactory = "topicListenerFactory")
    public void consumeMessage(String message) {
        logger.info("Message received from activemq queue---"+message);

        stockItems items=null;
        List<String> listsring= Arrays.asList(message.split("/"));
        if(listsring.size()==3)
        {
            String demande = listsring.get(1);

            if(demande.equals("demande")==true)
            {
                items=stockService.getBetterPriceDemande(listsring.get(2));

                try {
                    ObjectMapper mapper = new ObjectMapper();
                    String studentAsJson ="rien";

                    if(items!=null)
                    {
                        studentAsJson =items.getFournisseur()+"/"+ items.getIdstock()+"/"+items.getQuantite()+"/"+items.getPrix()+"/s";
                    }
                    logger.info("Message received from activemq queue---"+studentAsJson);
                    jmsTemplate.convertAndSend(new ActiveMQTopic(topic), studentAsJson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            if(listsring.size()==4)
            {
                if(listsring.get(0).equals("f")==true)
                {
                    String nomfournisseur = listsring.get(2);
                    if("haribo".equals(nomfournisseur)==true)
                    {
                        String libelle=listsring.get(3);
                        stockService.ChangeStock(libelle);
                    }
                }

            }
        }
    }
}
