package org.jewtiejew.tweeteater.listener;

import org.jewtiejew.tweeteater.keyword.KeywordCollector;
import org.jewtiejew.tweeteater.queue.QueueProcessor;
import org.jewtiejew.tweeteater.vo.TweetEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import twitter4j.Status;

/**
 * Created by mike on 07.06.18.
 */
@Service
public class QueueListener {

    Logger logger = LoggerFactory.getLogger(QueueListener.class);

    @Autowired
    KeywordCollector collector;

    @Autowired
    @Qualifier("InMemoryQueue")
    QueueProcessor<Status, TweetEntity> queue;

    volatile boolean isStop = false;

    Thread thread = null;

    public synchronized void start() {
        if (thread == null) {
            isStop = false;
            thread = new Thread(() -> {
                for (; !isStop; ) {

                    TweetEntity status = queue.get();
                    collector.accept(status);
                }

            });
            thread.start();
        }
    }

    public synchronized void stop() {
        if (thread == null && thread.isAlive()) {
            isStop = true;
            thread = null;
        }
    }

}
