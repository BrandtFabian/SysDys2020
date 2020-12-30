package com.example.demo;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import com.javasampleapproach.activemq.model.Product;

@Component
public class Class1 {
    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${jsa.activemq.queue.producer}")
    String queue;

    public void send(Product product, String companyName) {
        jmsTemplate.convertAndSend(queue, product, new MessagePostProcessor() {
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("company", companyName);
                return message;
            }
        });
    }
}
