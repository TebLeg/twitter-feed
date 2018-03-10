package za.co.application.tweet;

import org.springframework.util.StringUtils;
import za.co.application.common.Validator;

import java.util.Set;
import java.util.regex.Pattern;

import static za.co.application.tweet.TweetEnum.MAX_TWEET_CHARACTERS;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_START;

/**
 * Created by A100286 on 3/8/2018.
 */
public class TweetValidator extends Validator {

    /**
     * Validates the tweets based according to the business rules.
     * @param tweetLine
     * @param name
     * @throws IllegalArgumentException
     */
    public static void validate(String tweetLine, String name, Set<String> errorList) throws IllegalArgumentException{
        if(!tweetLine.contains(TWITTER_FEED_START.getValue())){
            errorList.add("Invalid tweet line pattern: " + tweetLine + ". Line skipped.");
            throw new IllegalArgumentException("Invalid tweet line pattern: " + tweetLine + ". Line skipped.");
        }

        if(!StringUtils.isEmpty(tweetLine) && tweetLine.substring((name + TWITTER_FEED_START.getValue() + " ").length()).length() > Integer.parseInt(MAX_TWEET_CHARACTERS.getValue())) {
            errorList.add("Invalid tweet length: " + tweetLine + ". Max characters is " + MAX_TWEET_CHARACTERS.getValue() + " Line skipped.");
            throw new IllegalArgumentException("Invalid tweet length: " + tweetLine + ". Max characters is " + MAX_TWEET_CHARACTERS.getValue() + ". Line skipped.");
        }

        validateAscii(tweetLine, errorList);

    }
}
