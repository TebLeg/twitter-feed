package za.co.application.user;

import java.util.Comparator;

/**
 * Used to enable sorting in alphabetical order.
 *
 * Created by A100286 on 3/8/2018.
 */
public class UserComparator  implements Comparator<User> {

    @Override
    public int compare(User obj1, User obj2) {
        return obj1.getName().compareTo(obj2.getName());
    }
}
