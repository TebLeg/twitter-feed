package za.co.application.tweet;

/**
 * Created by A100286 on 3/8/2018.
 */
public enum TweetEnum {

    TWITTER_FEED_START(">"),
    TWITTER_FEED_USER_AT("@"),
    TWITTER_FEED_COLON(":"),
    MAX_TWEET_CHARACTERS("120");

    private String value;

    TweetEnum(String description) {
        this.value = description;
    }

    public String getValue() {
        return value;
    }
}
