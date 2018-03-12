package za.co.application.user;

import za.co.application.common.Validator;
import java.util.Set;
import static za.co.application.user.UserEnum.USER_LINE_PATTERN;

/**
 * Created by A100286 on 3/8/2018.
 */
public class UserValidator extends Validator{

    /**
     * Used to validate whether the file conforms to the rules.
     * @param userLine
     * @throws IllegalArgumentException
     */
    public static void validate(String userLine, Set<String> errorList) throws IllegalArgumentException{

        if(!userLine.contains(USER_LINE_PATTERN.getValue())){
            errorList.add("Illegal pattern in user line: " + userLine + ". Line skipped.");
            throw new IllegalArgumentException("Illegal pattern in user line: " + userLine);
        }

        validateAscii(userLine, errorList);

    }
}
