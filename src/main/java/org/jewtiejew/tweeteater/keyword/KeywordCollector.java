package org.jewtiejew.tweeteater.keyword;

import org.jewtiejew.tweeteater.vo.TweetEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by mike on 06.06.18.
 */
@Service
public class KeywordCollector implements Consumer<TweetEntity>, Supplier<Map<String, Set<String>>> {

    ConcurrentMap<String, Map<String, Integer>> keywords = new ConcurrentHashMap<>();

    Logger logger = LoggerFactory.getLogger(KeywordCollector.class);

    @Autowired
    KeywordFilter keywordFilter;

    private static final Integer LIMIT_WORDS = 3;

    @Override
    public void accept(TweetEntity status) {
        String country = status.getCountry();
        if (country != null) {
            keywords.putIfAbsent(country, new HashMap<>());
            synchronized (keywords.get(country)) {
                final Map<String, Integer> keywordsByCountry = keywords.get(country);
                //Split by words and fill the map by country
                Arrays.stream(status.getText().split("\\P{L}+"))
                        .filter(keywordFilter)
                        .distinct()
                        .map(String::toLowerCase)
                        .forEach((w) -> {
                            keywordsByCountry.put(w, keywordsByCountry.getOrDefault(w, 0) + 1);
                        });
            }
            if (country.equals("Russia")) {
                logger.debug(status.getText());
            }

        }
    }

    @Override
    public Map<String, Set<String>> get() {
        final Map<String, Set<String>> result = new HashMap<>();
        keywords.keySet().forEach((k) -> {
            result.put(k, getKeywords(k));
        });
        return result;
    }

    private Set<String> getKeywords(String country) {
        Map<String, Integer> words = keywords.get(country);
        List<Map.Entry<String, Integer>> list = new LinkedList<>(words.entrySet());

        Collections.sort(list,
                (o1, o2) -> {return o2.getValue().compareTo(o1.getValue());});

        //return list.stream().limit(LIMIT_WORDS).map(Map.Entry::getKey).collect(Collectors.toSet());
        return list.stream().limit(LIMIT_WORDS).map((o) -> o.getKey() + ":" + o.getValue()).collect(Collectors.toSet());

    }

/*
    public static void main(String[] args) throws InterruptedException {

        TwitterStream stream = new TwitterStreamFactory().getInstance();
        InMemoryQueue queue = new InMemoryQueue();

        TweetsReader reader = new TweetsReader(stream, queue);
        reader.start();


        KeywordCollector collector = new KeywordCollector();
        new Thread( () -> {
            for(;;) {
                collector.accept(queue.get());
            }
        }).start();
        Thread.sleep(50000);

        collector.get().keySet().forEach((c) -> System.out.println(c + ": " + collector.getKeywords(c)));
    }
*/
}
