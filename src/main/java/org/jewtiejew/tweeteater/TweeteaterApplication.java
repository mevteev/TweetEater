package org.jewtiejew.tweeteater;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.jewtiejew.tweeteater.keyword.KeywordCollector;
import org.jewtiejew.tweeteater.twitter.TweetsReader;
import org.jewtiejew.tweeteater.listener.QueueListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import javax.jms.ConnectionFactory;

@SpringBootApplication
public class TweeteaterApplication {

    private static final String JMS_BROKER_URL = "vm://embedded?broker.persistent=false,useShutdownHook=false";

    @Autowired
    TweetsReader reader;

    @Autowired
    QueueListener listener;

    @Autowired
    KeywordCollector collector;

    Logger logger = LoggerFactory.getLogger(TweeteaterApplication.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(JMS_BROKER_URL);
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TweeteaterApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        logger.debug("init");

        reader.start();
        listener.start();

//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        listener.stop();
//        reader.stop();
//        logger.debug("stopped");


    }
}
