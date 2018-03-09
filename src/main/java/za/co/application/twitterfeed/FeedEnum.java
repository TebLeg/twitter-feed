package za.co.application.twitterfeed;

/**
 * Created by A100286 on 3/9/2018.
 */
public enum FeedEnum {
    NUMBER_OF_FILES("2"),
    USER_FILE_NAME("user.txt"),
    TWEET_FILE_NAME("tweet.txt"),
    USER_FILE_NAME_ARG_INDEX("0"),
    TWEET_FILE_NAME_ARG_INDEX("1");

    private String value;

    FeedEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
