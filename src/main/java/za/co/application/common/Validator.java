package za.co.application.common;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by A100286 on 3/8/2018.
 */
public class Validator {

    private static final String ASCII_PATTERN = "\\A\\p{ASCII}*\\z";

    /**
     * Validates whether the text is in ASCII format or not.
     * @param textLine
     * @throws IllegalArgumentException
     */
    public static void validateAscii(String textLine, Set<String> errorList) throws IllegalArgumentException{
        if(!Pattern.matches(ASCII_PATTERN, textLine)){
            errorList.add("Text is not in ASCII format: " + textLine + ". Line skipped.");
            throw new IllegalArgumentException("Text is not in ASCII format: " + textLine);
        }
    }
}
