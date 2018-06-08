package org.jewtiejew.tweeteater.queue;

import org.jewtiejew.tweeteater.vo.TweetEntity;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by mike on 08.06.18.
 */
public interface QueueProcessor<T, V> extends Consumer<T>, Supplier<V> {
}
