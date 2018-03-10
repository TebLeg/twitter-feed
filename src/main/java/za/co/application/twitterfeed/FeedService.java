package za.co.application.twitterfeed;

import za.co.application.tweet.TweetController;
import za.co.application.user.User;
import za.co.application.user.UserController;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static za.co.application.twitterfeed.FeedEnum.TWEET_FILE_NAME_ARG_INDEX;
import static za.co.application.twitterfeed.FeedEnum.USER_FILE_NAME_ARG_INDEX;

/**
 * Created by A100286 on 3/8/2018.
 */
public class FeedService {

    private Set<String> errorList = new HashSet<>();

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

            Path path = Paths.get(args[Integer.parseInt(TWEET_FILE_NAME_ARG_INDEX.getValue())]);
            if(Files.exists(path)) {
                TweetController tweetController = new TweetController();
                tweetController.execute(Paths.get(args[Integer.parseInt(TWEET_FILE_NAME_ARG_INDEX.getValue())]).toFile(),
                        userList, errorList);
            }
        } finally {
            System.out.println("=============================================================================================");
            System.out.println("Warnings and Errors list:");
            System.out.println(printErrorList());
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
}
