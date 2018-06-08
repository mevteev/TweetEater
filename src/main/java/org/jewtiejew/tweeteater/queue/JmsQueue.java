package org.jewtiejew.tweeteater.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import twitter4j.Status;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by mike on 07.06.18.
 */
@Service
public class JmsQueue implements Consumer<Status>, Supplier<Status> {

    private static final String TWEETS_QUEUE = "tweets.queue";

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsQueue(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void accept(Status status) {
        jmsTemplate.convertAndSend(TWEETS_QUEUE, status);
    }

    @Override
    public Status get() {
        return (Status)jmsTemplate.receiveAndConvert(TWEETS_QUEUE);
    }
}
