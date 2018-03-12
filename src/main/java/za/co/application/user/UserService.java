package za.co.application.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import static za.co.application.user.UserEnum.*;

/**
 * Created by A100286 on 3/12/2018.
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     * Adds as User objects the twitter names and the people they follow
     * @param userLine
     * @throws IllegalArgumentException
     */
    @Async
    public static CompletableFuture<Boolean> addUsersAndFollows(String userLine, Set<String> errorList, Set<User> userHashSet) throws IllegalArgumentException{
        try {

            UserValidator.validate(userLine, errorList);
            //Loop and create unique set of users.
            String[] tokens = userLine.split(USER_LINE_PATTERN.getValue());
            String name = tokens[Integer.parseInt(USER_INDEX.getValue())].trim();

            User parent = createUser(name, userHashSet);
            Set<User> childrenSet = new HashSet<>();
            String[] childrenArr = tokens[Integer.parseInt(FOLLOWED_USERS_INDEX.getValue())].split(FOLLOWED_USERS_REGEX.getValue());
            //Create the User objects of the children and add them to the current object's children list.
            for(String child : childrenArr) {
                User childUser = createUser(child, userHashSet);

                childrenSet.add(childUser);
            }
            parent.setFollows(childrenSet);

        } catch (IllegalArgumentException e) {
            //Do nothing
        }
        return CompletableFuture.completedFuture(true);
    }

    /**
     * Creates a single unique user.
     * @param name
     * @return
     */

    private static User createUser(String name, Set<User> userHashSet) {
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
