package za.co.application.user;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import java.io.File;
import java.io.IOException;
import java.util.*;
import static za.co.application.user.UserEnum.*;

/**
 * Created by A100286 on 3/8/2018.
 */
public class UserController {

    private Set<User> userHashSet = new HashSet<>();

    /**
     * Executes the business logic by delegating.
     * @param userFile
     * @param errorList
     * @return
     * @throws IllegalArgumentException
     */

    public List<User> execute(File userFile, Set<String> errorList) throws IllegalArgumentException {
        try {
            return parseUsers(userFile, errorList);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Creates unique list of the users (those being followed and followers).
     * Uses file streaming to read large files efficiently.
     * @param userFile
     * @param errorList
     */

    private List<User> parseUsers(File userFile, Set<String> errorList) throws IllegalArgumentException{

        LineIterator it = null;
        String line = null;
        try {
            try {
                it = FileUtils.lineIterator(userFile, "US-ASCII");
            } catch (IOException e) {
                throw new IllegalArgumentException("Directory or file does not exist");
            }
            while (it.hasNext()) {
                line = it.nextLine();
                try {
                    UserValidator.validate(line, errorList);
                    addUsersAndFollows(line);

                } catch (IllegalArgumentException e) {
                    //Just skip this line.
                    continue;
                }
            }

        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Illegal pattern in user line: " + line);
        } finally {
            LineIterator.closeQuietly(it);
        }
        System.out.println("======================================= Twitter Users ========================================");
        System.out.println(userHashSet);
        return new ArrayList(userHashSet);
    }

    /**
     * Adds as User objects the twitter names and the people they follow
     * @param userLine
     * @throws IllegalArgumentException
     */
    private void addUsersAndFollows(String userLine) throws IllegalArgumentException{
        try {

            //Loop and create unique set of users.
            String[] tokens = userLine.split(USER_LINE_PATTERN.getValue());
            String name = tokens[Integer.parseInt(USER_INDEX.getValue())].trim();

            User parent = createUser(name);
            Set<User> childrenSet = new HashSet<>();
            String[] childrenArr = tokens[Integer.parseInt(FOLLOWED_USERS_INDEX.getValue())].split(FOLLOWED_USERS_REGEX.getValue());
            //Create the User objects of the children and add them to the current object's children list.
            for(String child : childrenArr) {
                User childUser = createUser(child);

                childrenSet.add(childUser);
            }
            parent.setFollows(childrenSet);

        } catch (IllegalArgumentException e) {
            //Do nothing
        }
    }

    /**
     * Creates a single unique user.
     * @param name
     * @return
     */

    private User createUser(String name) {
        User user = new User(name.trim());
        if(!userHashSet.contains(user)) {
            userHashSet.add(user);
        } else{
            //Get the existing user in in order to add children
            for(User tempUserObj : userHashSet) {
                if(tempUserObj.equals(user)) {
                    user = tempUserObj;
                    break;
                }
            }
        }
        return user;
    }

}
