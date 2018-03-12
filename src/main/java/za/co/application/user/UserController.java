package za.co.application.user;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by A100286 on 3/8/2018.
 */
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private Set<User> userHashSet = ConcurrentHashMap.newKeySet();

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
                CompletableFuture<Boolean> booleanCompletableFuture = UserService.addUsersAndFollows(line, errorList, userHashSet);
                //Block and wait for it to complete, we want to wait for all the async tasks before we continue otherwise we have an incomplete twitter feed
                booleanCompletableFuture.get();
            }

        }catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.warn("Illegal pattern in user line: " + line);
        } catch (InterruptedException e) {
            errorList.add("InterruptedException, thread interrupted - line skipped.");
        } catch (ExecutionException e) {
            errorList.add("ExecutionException, line skipped.");
        } finally {
            LineIterator.closeQuietly(it);
        }
        LOGGER.info("======================================= Twitter Users ========================================");
        List<User> userList = new ArrayList(userHashSet);
        //Sort the user list in alphabetical order.
        UserComparator comparator = new UserComparator();
        Collections.sort(userList, comparator);
        LOGGER.info(userList.toString());
        return userList;
    }
}
