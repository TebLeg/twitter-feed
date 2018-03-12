package za.co.application.twitterfeed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import za.co.application.tweet.TweetController;
import za.co.application.tweet.TweetValidator;
import za.co.application.user.User;
import za.co.application.user.UserController;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_COLON;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_START;
import static za.co.application.tweet.TweetEnum.TWITTER_FEED_USER_AT;
import static za.co.application.twitterfeed.FeedEnum.*;

/**
 * Created by A100286 on 3/8/2018.
 */
@Service
public class FeedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedService.class);
    private Set<String> errorList = ConcurrentHashMap.newKeySet();

    /**
     * Executes the business logic of the application.
     * @param args
     * @throws IllegalArgumentException
     */
    public void execute(String[] args) throws IllegalArgumentException {
        try {

            ArgumentsValidator.validate(args, errorList);

            UserController userController = new UserController();
            List<User> userList = userController.execute(Paths.get(args[Integer.parseInt(USER_FILE_NAME_ARG_INDEX.getValue())]).toFile(),
                    errorList);

            if (!userList.isEmpty()) {
                Path path = Paths.get(args[Integer.parseInt(TWEET_FILE_NAME_ARG_INDEX.getValue())]);
                if (Files.exists(path)) {
                    TweetController tweetController = new TweetController();
                    tweetController.execute(Paths.get(args[Integer.parseInt(TWEET_FILE_NAME_ARG_INDEX.getValue())]).toFile(),
                            userList, errorList);
                }
            } else {
                errorList.add("Empty users file " + USER_FILE_NAME.getValue());
            }

        } catch (Exception e) {
            //Do nothing because all errors are logged on the console
        } finally {
            LOGGER.info("=============================================================================================");
            LOGGER.info("Warnings and Errors list:");
            LOGGER.info(printErrorList());
        }
    }

    /**
     * A utility method to just print the list of warning and errors.
     * @return
     */
    public String printErrorList() {
        System.out.println("=============================================================================================");
        if(errorList.isEmpty()) {
            return "There are no warnings or errors.";
        } else {
            StringBuilder sbError = new StringBuilder();
            for(String error : errorList) {
                sbError.append(error).append(System.getProperty("line.separator"));
            }
            return sbError.toString();
        }
    }

    /**
     * Asynchronously executes the task to display the feed for each user.
     * @param line
     * @param errorList
     * @param user
     * @return
     * @throws IllegalArgumentException
     */
    @Async
    public static CompletableFuture<Boolean> displayFeed(String line, Set<String> errorList, User user) throws IllegalArgumentException{
        try {
            TweetValidator.validate(line, user.getName(), errorList);
            //Display the tweets of user
            if(line.startsWith(user.getName())) {

                if(line.substring(user.getName().length()).charAt(0) == TWITTER_FEED_START.getValue().charAt(0)) {
                    LOGGER.info(TWITTER_FEED_USER_AT.getValue() +
                            line.replace(TWITTER_FEED_START.getValue(), TWITTER_FEED_COLON.getValue()));
                } else {
                    errorList.add("Invalid tweet line pattern: " + line + ". Line skipped.");
                }

            }
            //Now display the tweets of the users that they follow
            user.getFollows().stream().filter(follows -> line.startsWith(follows.getName())).forEach(follows -> {

                if (line.substring(follows.getName().length()).charAt(0) == TWITTER_FEED_START.getValue().charAt(0)) {
                    LOGGER.info(TWITTER_FEED_USER_AT.getValue() +
                            line.replace(TWITTER_FEED_START.getValue(), TWITTER_FEED_COLON.getValue()));
                } else {
                    errorList.add("Invalid tweet line pattern: " + line + ". Line skipped.");
                }
            });
        } catch (IllegalArgumentException e) {
            //Do nothing
        }
        return CompletableFuture.completedFuture(true);
    }
}
