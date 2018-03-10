package za.co.application.user;

/**
 * Fixed constants used by the User feature.
 * Created by A100286 on 3/8/2018.
 */

public enum UserEnum {
    USER_LINE_PATTERN(" follows"),
    USER_INDEX("0"),
    FOLLOWED_USERS_INDEX("1"),
    FOLLOWED_USERS_REGEX(",");

    private String value;

    UserEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
