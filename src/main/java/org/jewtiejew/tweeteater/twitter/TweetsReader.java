package org.jewtiejew.tweeteater.twitter;

import org.jewtiejew.tweeteater.queue.QueueProcessor;
import org.jewtiejew.tweeteater.vo.TweetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import twitter4j.*;

/**
 * Created by mike on 05.06.18.
 */
@Service
public class TweetsReader {

    TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

    @Autowired
    @Qualifier("InMemoryQueue")
    QueueProcessor<Status, TweetEntity> tweetsConsumer;

    volatile Boolean isRunning = false;

    public void start() {
        if (isRunning)
            return;

        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                tweetsConsumer.accept(status);
            }
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
            public void onException(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }
        };

        twitterStream.addListener(listener);
        twitterStream.sample();
        isRunning = true;

    }

    public void stop() {
        if (isRunning) {
            twitterStream.clearListeners();
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
