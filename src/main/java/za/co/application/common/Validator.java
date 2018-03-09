package za.co.application.common;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by A100286 on 3/8/2018.
 */
public abstract class Validator {

    private static final String ASCII_PATTERN = "\\A\\p{ASCII}*\\z";
    private static Set<String> errorList = new HashSet<>();

    public static void validateAscii(String textLine) throws IllegalArgumentException{
        if(!Pattern.matches(ASCII_PATTERN, textLine)){
            Validator.setError("Text is not in ASCII format: " + textLine + ". Line skipped.");
            throw new IllegalArgumentException("Text is not in ASCII format: " + textLine);
        }
    }

    public static void setError(String error) {
        errorList.add(error);
    }

    public static void printErrorList() {
        System.out.println("=============================================================================================");
        if(errorList.isEmpty()) {
            System.out.println("There are no errors.");
        } else {
            StringBuilder sbError = new StringBuilder("Error list:");
            sbError.append(System.getProperty("line.separator"));
            for(String error : errorList) {
                sbError.append(error).append(System.getProperty("line.separator"));
            }
            System.out.println(sbError.toString());
        }

        System.out.println("=============================================================================================");
    }
}
