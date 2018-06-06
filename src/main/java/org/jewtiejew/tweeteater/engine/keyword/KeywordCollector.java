package org.jewtiejew.tweeteater.engine.keyword;

import org.jewtiejew.tweeteater.engine.twitter.TweetsReader;
import twitter4j.Status;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by mike on 06.06.18.
 */
public class KeywordCollector implements Consumer<Status>, Supplier<Map<String, Set<String>>> {

    ConcurrentMap<String, Map<String, Integer>> keywords = new ConcurrentHashMap<>();

    private static final Integer LIMIT_WORDS = 3;

    @Override
    public void accept(Status statusStream) {
        String country = statusStream.getPlace().getCountry();
        if (country != null) {
            keywords.putIfAbsent(country, new HashMap<>());
            synchronized (keywords.get(country)) {
                final Map<String, Integer> keywordsByCountry = keywords.get(country);
                //Split by words and fill the map by country
                Arrays.stream(statusStream.getText().split("\\P{L}+"))
                        .filter((s) -> s.length()> 3)
                        .distinct()
                        .map(String::toLowerCase)
                        .forEach((w) -> {
                            keywordsByCountry.put(w, keywordsByCountry.getOrDefault(w, 0) + 1);
                        });
                //keywords.put(country, keywordsByCountry);
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

    public static void main(String[] args) throws InterruptedException {
        TweetsReader reader = new TweetsReader();
        KeywordCollector collector = new KeywordCollector();
        new Thread( () -> reader.get().forEach(collector)).start();
        //new Thread( () -> reader.get().forEach(System.out::println)).start();
        Thread.sleep(150000);

        System.out.println(collector.get());
    }



}
