package za.co.application.tweet;

import za.co.application.user.User;

import java.util.Arrays;

/**
 * Created by A100286 on 3/8/2018.
 */
public class Tweet {
    private final User user;
    private final String[] textList;

    public Tweet(User user, String[] textList) {
        this.user = user;
        this.textList = textList;
    }

    public User getUser() {
        return user;
    }

    public String[] getText() {
        return textList;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "user=" + user +
                ", textList=" + Arrays.toString(textList) +
                '}';
    }
}
