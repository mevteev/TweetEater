package org.jewtiejew.tweeteater.keyword;

import org.jewtiejew.tweeteater.vo.TweetEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by mike on 08.06.18.
 */
public class KeywordCollectorTest {

    @InjectMocks
    KeywordCollector collector;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void testCollector() {

        getData1().forEach(collector);

        Map<String, Set<String>> result = collector.get();

        assertEquals(2, result.size());
        assertArrayEquals(result.get("Russia").toArray(), new String[] {"house:3", "weather:3", "street:2"});
    }

    private List<TweetEntity> getData1() {
        List<TweetEntity> statuses = new LinkedList<>();
        statuses.add(new TweetEntity().withCountry("Russia").withText("house street weather dom"));
        statuses.add(new TweetEntity().withCountry("Russia").withText("house tree grass house"));
        statuses.add(new TweetEntity().withCountry("Russia").withText("street weather dom grass"));
        statuses.add(new TweetEntity().withCountry("Bosnia").withText("house street weather dom"));
        statuses.add(new TweetEntity().withCountry("Russia").withText("houSe horse weather"));

        return statuses;
    }

}
