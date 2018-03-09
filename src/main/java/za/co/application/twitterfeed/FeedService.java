package za.co.application.twitterfeed;

import za.co.application.common.Validator;
import za.co.application.tweet.TweetController;
import za.co.application.user.User;
import za.co.application.user.UserController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static za.co.application.twitterfeed.FeedEnum.TWEET_FILE_NAME_ARG_INDEX;
import static za.co.application.twitterfeed.FeedEnum.USER_FILE_NAME_ARG_INDEX;

/**
 * Created by A100286 on 3/8/2018.
 */
public class FeedService {

    /**
     * Executes the business logic of the application.
     * @param args
     * @throws IllegalArgumentException
     */
    public void execute(String[] args) throws IllegalArgumentException {
        try {
            ArgumentsValidator.validate(args);

            UserController userController = new UserController();
            List<User> userList = userController.execute(Paths.get(args[Integer.parseInt(USER_FILE_NAME_ARG_INDEX.getValue())]).toFile());

            Path path = Paths.get(args[Integer.parseInt(TWEET_FILE_NAME_ARG_INDEX.getValue())]);
            if(Files.exists(path)) {
                TweetController tweetController = new TweetController();
                tweetController.execute(Paths.get(args[Integer.parseInt(TWEET_FILE_NAME_ARG_INDEX.getValue())]).toFile(), userList);
            }
        } finally {
            Validator.printErrorList();
        }
    }

}
