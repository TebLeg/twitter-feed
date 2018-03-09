package za.co.application.twitterfeed;

import za.co.application.common.Validator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public static void validate(String[] args) throws IllegalArgumentException {

        if(args.length != Integer.parseInt(NUMBER_OF_FILES.getValue())) {
            Validator.setError("Number of arguments is invalid, there should be " + NUMBER_OF_FILES.getValue());
            throw new IllegalArgumentException("Number of arguments is invalid, there should be " + NUMBER_OF_FILES.getValue());
        }

        Path path1 = Paths.get(args[0]);
        if(!Files.exists(path1)) {
            Validator.setError("File " + args[0] + " not found.");
            throw new IllegalArgumentException("File " + args[0] + " not found.");
        }
        String userFileName = path1.getFileName().toString();

        if(!userFileName.equals(USER_FILE_NAME.getValue())) {
            Validator.setError("Invalid user file name: " + args[0] + ". It should be " + USER_FILE_NAME.getValue());
            throw new IllegalArgumentException("Invalid user file name: " + args[0] + ". It should be " + USER_FILE_NAME.getValue());
        }

        Path path2 = Paths.get(args[1]);
        if(!Files.exists(path2)) {
            System.out.println("File " + args[1] + " not found.");
        } else {
            String tweetFileName = path2.getFileName().toString();

            if(!tweetFileName.equals(TWEET_FILE_NAME.getValue())) {
                Validator.setError("Invalid tweet file name: " + args[0] + ". It should be " + TWEET_FILE_NAME.getValue());
                throw new IllegalArgumentException("Invalid tweet file name: " + args[0] + ". It should be " + TWEET_FILE_NAME.getValue());
            }
        }

    }
}
