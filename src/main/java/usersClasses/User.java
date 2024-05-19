package usersClasses;

import lombok.Getter;

@Getter
public class User {
    private int userId;
    private final String username;
    private final String password;
    private final String email;
    private boolean isAdministrator;

    public User(
            int userId, String username, String password, String email, boolean isAdministrator) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdministrator = isAdministrator;
    }

    public User(String username, String password, String email, boolean isAdministrator) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdministrator = isAdministrator;
    }

    public void setAdministrator(boolean b) {
        isAdministrator = b;
    }

    @Override
    public String toString() {
        return "User{"
                + "userId="
                + userId
                + ", username='"
                + username
                + '\''
                + ", password='"
                + password
                + '\''
                + ", email='"
                + email
                + '\''
                + ", isAdministrator="
                + isAdministrator
                + '}';
    }

    public boolean getIsAdministrator() {
        return isAdministrator;
    }
}
