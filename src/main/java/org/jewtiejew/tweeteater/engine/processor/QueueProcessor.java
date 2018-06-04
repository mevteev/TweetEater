package org.jewtiejew.tweeteater.engine.processor;

import org.jewtiejew.tweeteater.engine.consumer.Consumer;
import org.jewtiejew.tweeteater.engine.producer.Producer;

public interface QueueProcessor<P extends Producer, C extends Consumer> {

    void process(P producer, C consumer);
}
