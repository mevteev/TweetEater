package org.jewtiejew.tweeteater.engine.consumer;

import java.util.stream.Stream;

public interface Consumer<C> {

    void consume(C item);

    void consumeStream(Stream<C> itemStream);


}
