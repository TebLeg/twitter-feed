package za.co.application.user;

import java.util.Comparator;
import java.util.Set;

/**
 * Created by A100286 on 3/8/2018.
 */
public class User {

    private String name;
    private Set<String> follows;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFollows(Set<String> follows) {
        this.follows = follows;
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





    public Set<String> getFollows() {
        return follows;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", follows=" + follows +
                '}';
    }

}
