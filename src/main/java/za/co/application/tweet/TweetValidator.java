package za.co.application.tweet;

import za.co.application.common.Validator;

import java.util.regex.Pattern;

import static za.co.application.tweet.TweetEnum.MAX_TWEET_CHARACTERS;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_START;

/**
 * Created by A100286 on 3/8/2018.
 */
public class TweetValidator extends Validator {

    public static void validate(String tweetLine, String name) throws IllegalArgumentException{
        if(Pattern.matches(TWITTER_FEED_START.getValue() + " ", tweetLine)){
            Validator.setError("Invalid tweet line pattern: " + tweetLine + ". Line skipped.");
        }

        if(tweetLine.substring((name + TWITTER_FEED_START.getValue() + " ").length()).length() > Integer.parseInt(MAX_TWEET_CHARACTERS.getValue())) {
            Validator.setError("Invalid tweet length: " + tweetLine + ". Line skipped.");
        }

        /*if(tweetLine.substring(name.length()).charAt(0) != TWITTER_FEED_START.getValue().charAt(0)) {
            Validator.setError("Invalid tweet line pattern: " + tweetLine + ". Line skipped.");
            throw new IllegalArgumentException("Invalid tweet line pattern: " + tweetLine + ". Line will be skipped.");
        }*/

        validateAscii(tweetLine);

    }
}
