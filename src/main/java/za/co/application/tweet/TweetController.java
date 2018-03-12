package za.co.application.tweet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.application.twitterfeed.FeedService;
import za.co.application.user.User;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/**
 * Created by A100286 on 3/8/2018.
 */
public class TweetController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TweetController.class);
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

        LOGGER.info("======================================= Twitter Feed ========================================");

        for (User user : userList) {
            LOGGER.info(user.getName());

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

                    CompletableFuture<Boolean> booleanCompletableFuture = FeedService.displayFeed(line, errorList, user);
                    //Block and wait for it to complete, we want to wait for all the async tasks before we continue otherwise we have an incomplete twitter feed
                    booleanCompletableFuture.get();

                }
            } catch (InterruptedException e) {
                errorList.add("InterruptedException, thread interrupted - line skipped.");
            } catch (ExecutionException e) {
                errorList.add("ExecutionException, line skipped.");
            } finally {
                LineIterator.closeQuietly(it);
            }
        }
    }
}
