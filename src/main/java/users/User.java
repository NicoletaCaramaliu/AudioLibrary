package users;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {
    private int userId;
    private final String username;
    private final String password;
    private final String email;
    @Setter
    private Roles role;

    public User(int userId, String username, String password, String email, Roles role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String username, String password, String email, Roles role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

}
