package org.jewtiejew.tweeteater.engine.twitter;

import twitter4j.*;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by mike on 05.06.18.
 */
public class TweetsReader implements Supplier<Stream<Status>> {

    TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

    BlockingQueue<Status> queue = new LinkedBlockingQueue<>();

    @Override
    public Stream<Status> get() {
        StatusListener listener = new StatusListener(){
            public void onStatus(Status status) {
                //System.out.println(status.getUser().getName() + " : " + status.getLang());
                try {
                    if (status.getPlace() != null) {
                        queue.put(status);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        return Stream.generate(() -> {
            try {
                return queue.take();
            } catch (InterruptedException ie) {
                return null;
            }
        });

    }

    public static void main(String[] args) {
        TweetsReader reader = new TweetsReader();
//        reader.get();
        reader.get().forEach(
                (s) -> System.out.println(
                        s.getUser().getName() + " : " + s.getText() + " : " +
                                s.getPlace().getCountry() + " : " +
                                s.getPlace().getName() ));
        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reader.twitterStream.shutdown();*/


        /*for(;;) {
            Status s = null;
            try {
                s = reader.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (s!= null) {
                System.out.println(
                        s.getUser().getName() + " : " + s.getText());
            }
        }*/


    }
}
