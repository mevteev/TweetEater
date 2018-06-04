package org.jewtiejew.tweeteater.engine.producer;

import java.util.stream.Stream;

public interface Producer<P> {

    P produce();

    Stream<P> produceStream() throws Exception;

}
