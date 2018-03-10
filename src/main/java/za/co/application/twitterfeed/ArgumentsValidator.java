package za.co.application.twitterfeed;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import static za.co.application.twitterfeed.FeedEnum.NUMBER_OF_FILES;
import static za.co.application.twitterfeed.FeedEnum.TWEET_FILE_NAME;
import static za.co.application.twitterfeed.FeedEnum.USER_FILE_NAME;

/**
 * Created by A100286 on 3/9/2018.
 */
public class ArgumentsValidator {

    /**
     * Validates the arguments used to invoke the application.
     */

    public static void validate(String[] args, Set<String> errorList) throws IllegalArgumentException {

        if(args.length != Integer.parseInt(NUMBER_OF_FILES.getValue())) {
            errorList.add("Number of arguments is invalid, there should be " + NUMBER_OF_FILES.getValue());
            throw new IllegalArgumentException("Number of arguments is invalid, there should be " + NUMBER_OF_FILES.getValue());
        }

        Path path1 = Paths.get(args[0]);
        if(!Files.exists(path1)) {
            errorList.add("File " + args[0] + " not found.");
            throw new IllegalArgumentException("File " + args[0] + " not found.");
        }
        String userFileName = path1.getFileName().toString();

        if(!userFileName.equals(USER_FILE_NAME.getValue())) {
            errorList.add("Invalid user file name: " + args[0] + ". It should be " + USER_FILE_NAME.getValue());
            throw new IllegalArgumentException("Invalid user file name: " + args[0] + ". It should be " + USER_FILE_NAME.getValue());
        }

        Path path2 = Paths.get(args[1]);
        if(!Files.exists(path2)) {
            errorList.add("File " + args[1] + " not found.");
        } else {
            String tweetFileName = path2.getFileName().toString();

            if(!tweetFileName.equals(TWEET_FILE_NAME.getValue())) {
                errorList.add("Invalid tweet file name: " + args[1] + ". It should be " + TWEET_FILE_NAME.getValue());
                throw new IllegalArgumentException("Invalid tweet file name: " + args[1] + ". It should be " + TWEET_FILE_NAME.getValue());
            }
        }
    }
}
