package org.jewtiejew.tweeteater.engine.twitter;

import twitter4j.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by mike on 05.06.18.
 */
public class TweetsReader implements Supplier<Stream<String>> {

    Twitter twitter = TwitterFactory.getSingleton();

    @Override
    public Stream<String> get() {
        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
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

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(listener);
        twitterStream.sample();
        return null;

    }

    public static void main(String[] args) {
        new TweetsReader().get();
    }
}
