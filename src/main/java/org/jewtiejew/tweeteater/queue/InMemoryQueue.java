package org.jewtiejew.tweeteater.queue;

import org.jewtiejew.tweeteater.Converter;
import org.jewtiejew.tweeteater.vo.TweetEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import twitter4j.Status;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by mike on 07.06.18.
 */
@Component("InMemoryQueue")
public class InMemoryQueue implements QueueProcessor<Status, TweetEntity> {

    @Autowired
    Converter converter;

    BlockingQueue<TweetEntity> queue = new LinkedBlockingQueue<>();

    Logger logger = LoggerFactory.getLogger(InMemoryQueue.class);

    @Override
    public void accept(Status status) {
        TweetEntity tweetEntity = converter.toTweetEntity(status);
        try {
            if (tweetEntity.getCountry() != null) {
                queue.put(tweetEntity);
            }
        } catch (InterruptedException e) {
            logger.error("Error putting tweet to queue", e);
        }
    }

    @Override
    public TweetEntity get() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            logger.error("Error getting tweet from queue", e);
        }
        return null;
    }

}
