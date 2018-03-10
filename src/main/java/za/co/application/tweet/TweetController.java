package za.co.application.tweet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import za.co.application.user.User;
import za.co.application.user.UserComparator;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_COLON;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_START;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_USER_AT;


/**
 * Created by A100286 on 3/8/2018.
 */
public class TweetController {

    /**
     * Executes the business logic by delegating.
     * @param tweetFile
     * @param userList
     * @throws IOException
     */
    public void execute(File tweetFile, List<User> userList, Set<String> errorList) {
        try {
            parseAndDisplayTwitterFeed(tweetFile, userList, errorList);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts the tweets, parses them and display the feed.
     * @param tweetFile
     * @param userList
     * @throws IllegalArgumentException
     * @throws IOException
     */
    private void parseAndDisplayTwitterFeed(File tweetFile, List<User> userList, Set<String> errorList) throws IllegalArgumentException {
        //Sort the user list in alphabetical order.
        UserComparator comparator = new UserComparator();
        Collections.sort(userList, comparator);
        System.out.println("======================================= Twitter Feed ========================================");

        for (User user : userList) {
            System.out.println(user.getName());

            LineIterator it;
            try {
                //Use line iterator because of massive files
                it = FileUtils.lineIterator(tweetFile, "US-ASCII");
            } catch (IOException e) {
                throw new IllegalArgumentException("Directory or file does not exist.");
            }
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    try {
                        TweetValidator.validate(line, user.getName(), errorList);
                        //Display the tweets of user
                        if(line.startsWith(user.getName())) {

                            if(line.substring(user.getName().length()).charAt(0) == TWITTER_FEED_START.getValue().charAt(0)) {
                                System.out.println(TWITTER_FEED_USER_AT.getValue() +
                                        line.replace(TWITTER_FEED_START.getValue(), TWITTER_FEED_COLON.getValue()));
                            } else {
                                errorList.add("Invalid tweet line pattern: " + line + ". Line skipped.");
                            }

                        }
                        //Now display the tweets of the users that they follow
                        user.getFollows().stream().filter(follows -> line.startsWith(follows.getName())).forEach(follows -> {

                            if (line.substring(follows.getName().length()).charAt(0) == TWITTER_FEED_START.getValue().charAt(0)) {
                                System.out.println(TWITTER_FEED_USER_AT.getValue() +
                                        line.replace(TWITTER_FEED_START.getValue(), TWITTER_FEED_COLON.getValue()));
                            } else {
                                errorList.add("Invalid tweet line pattern: " + line + ". Line skipped.");
                            }
                        });
                    } catch (IllegalArgumentException e) {
                        continue;
                    }
                }
            } finally {
                LineIterator.closeQuietly(it);
            }
        }
    }
}
