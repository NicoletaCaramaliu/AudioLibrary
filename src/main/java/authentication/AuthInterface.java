package authentication;

import java.util.List;

public interface AuthInterface {
    String login(List<User> users, String username, String password);

    User register(List<User> users, String username, String password, String email);

    String logout();

    User promote(List<User> users, String username);
}
