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

    private Set<String> userHashSet = new HashSet<>();
    private List<User> userList = new ArrayList<>();

    /**
     * Executes the business logic by delegating.
     * @param userFile
     * @return
     * @throws IllegalArgumentException
     */
    public List<User> execute(File userFile) throws IllegalArgumentException {
        try {
            return parseUsers(userFile);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * Parses the contents of the file lines into User objects.
     * @param userFile
     * @return
     * @throws IllegalArgumentException
     */

    private List<User> parseUsers(File userFile) throws IllegalArgumentException{

        addUsers(userFile);

        //Add following
        for(String userName : userHashSet) {
            Set<String> followedUsers = new HashSet<>();
            User user = new User(userName);

            LineIterator it;
            try {
                it = FileUtils.lineIterator(userFile, "US-ASCII");
            } catch (IOException e) {
                throw new IllegalArgumentException("Directory or file does not exist");
            }
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    try {
                        if(line.startsWith(userName)) {
                            String[] tokens =  line.split(USER_LINE_PATTERN.getValue());
                            try {
                                String[] followedNames = tokens[Integer.parseInt(FOLLOWED_USERS_INDEX.getValue())].split(FOLLOWED_USERS_REGEX.getValue());
                                for (String name : followedNames) {
                                    followedUsers.add(name.trim());
                                }
                            } catch (IndexOutOfBoundsException e) {
                                //Do nothing
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        //Do nothing
                    }
                }
            } finally {
                LineIterator.closeQuietly(it);
            }

            user.setFollows(followedUsers);
            userList.add(user);
        }
        System.out.println("======================================= Twitter Users ========================================");
        System.out.println(userList);
        return userList;
    }

    /**
     * Creates unique list of the users (those being followed and followers).
     * Uses file streaming to read large files efficiently.
     * @param userFile
     */
    private void addUsers(File userFile) throws IllegalArgumentException{

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
                    UserValidator.validate(line);
                    //Loop and create unique set of users.
                    String[] tokens = line.split(USER_LINE_PATTERN.getValue());
                    String name = tokens[Integer.parseInt(USER_INDEX.getValue())].trim();
                    userHashSet.add(name);
                    String[] followedUserList = tokens[Integer.parseInt(FOLLOWED_USERS_INDEX.getValue())].split(",");
                    for (String followedUser : followedUserList) {
                        userHashSet.add(followedUser.trim());
                    }
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
    }

}
