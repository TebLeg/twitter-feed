package za.co.application.user;

import java.util.HashSet;
import java.util.Set;

/**
 * The model class to store users in memory while processing.
 * Created by A100286 on 3/8/2018.
 */
public class User {

    private String name;
    private Set<User> follows = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setFollows(Set<User> follows) {
        this.follows = follows;
    }

    public Set<User> getFollows() {
        return follows;
    }

    /**
     * Custom implementation so that HashSet uses unique Users.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return name.equals(user.name);

    }

    /**
     * Custom implementation so that HashSet uses unique Users.
     * @return
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", follows=").append(follows);
        sb.append('}');
        return sb.toString();
    }
}
