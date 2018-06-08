package org.jewtiejew.tweeteater.keyword;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 * Created by mike on 08.06.18.
 */
@Component
public class KeywordFilter implements Predicate<String> {

    @Override
    public boolean test(String s) {
        return s.length() > 3 && !s.matches("http(.*)");
    }
}
