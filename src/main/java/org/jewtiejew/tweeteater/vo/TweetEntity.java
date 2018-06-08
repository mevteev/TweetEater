package org.jewtiejew.tweeteater.vo;

/**
 * Created by mike on 08.06.18.
 */
public class TweetEntity {

    private String user;
    private String country;
    private String text;

    public TweetEntity withUser(String user) {
        this.user = user;
        return this;
    }

    public TweetEntity withCountry(String country) {
        this.country = country;
        return this;
    }

    public TweetEntity withText(String text) {
        this.text = text;
        return this;
    }

    public String getUser() {
        return user;
    }

    public String getCountry() {
        return country;
    }

    public String getText() {
        return text;
    }
}
