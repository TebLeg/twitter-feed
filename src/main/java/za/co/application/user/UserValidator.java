package za.co.application.user;

import org.springframework.util.StringUtils;
import za.co.application.common.Validator;

import java.util.regex.Pattern;

import static za.co.application.user.UserEnum.USER_LINE_PATTERN;

/**
 * Created by A100286 on 3/8/2018.
 */
public class UserValidator extends Validator{

    /**
     * Used to validate whteher the file conforms to the rules.
     * @param userLine
     * @throws IllegalArgumentException
     */
    public static void validate(String userLine) throws IllegalArgumentException{

        if(!userLine.contains(USER_LINE_PATTERN.getValue())){
            //Assumption is that if the line in user.txt doesn't contain whitespaces then that't the user's name
            if(StringUtils.containsWhitespace(userLine)) {
                Validator.setError("Illegal pattern in user line: " + userLine + ". Line skipped.");
                throw new IllegalArgumentException("Illegal pattern in user line: " + userLine);
            }
        }

        validateAscii(userLine);

    }
}
