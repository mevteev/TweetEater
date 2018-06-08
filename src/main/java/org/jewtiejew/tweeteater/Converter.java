package org.jewtiejew.tweeteater;

import org.jewtiejew.tweeteater.vo.TweetEntity;
import org.springframework.stereotype.Component;
import twitter4j.Status;

/**
 * Created by mike on 08.06.18.
 */
@Component
public class Converter {

    public TweetEntity toTweetEntity(Status status) {
        return new TweetEntity().
                withUser(status.getUser().getName()).
                withText(status.getText()).
                withCountry(getCountry(status));

    }

    private String getCountry(Status status) {
        if (status.getPlace() != null && status.getPlace().getCountry() != null) {
            return status.getPlace().getCountry();
        }

        //return status.getUser().getLocation();
        return null;
    }
}
