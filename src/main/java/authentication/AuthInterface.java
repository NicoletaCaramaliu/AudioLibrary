package authentication;

import java.util.List;
import usersClasses.User;

public interface AuthInterface {
    public String login(List<User> users, String username, String password);

    public User register(List<User> users, String username, String password, String email);

    public String logout();

    public User promote(List<User> users, String username);
}
