package authentication;

import java.util.List;
import java.util.Objects;

import users.Roles;
import users.User;
import exceptions.InvalidCredentialsException;
import exceptions.UserAlreadyExistsException;

public record Authentication(SessionManager session) implements AuthInterface {

    public String login(List<User> users, String username, String password) {
        if (!session.isAnonymous()) {
            return "You are already logged in.";
        }

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                session.setCurrentUser(user);
                return "You are now authenticated as " + username;
            }
        }

        throw new InvalidCredentialsException("Username or password is invalid. Please try again!");
    }

    public User register(List<User> users, String username, String password, String email) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                throw new UserAlreadyExistsException("Username already exists. Please try again!");
            }
        }

        User newUser;
        if (users.isEmpty()) {
            newUser = new User(0, username, password, email, Roles.ADMINISTRATOR);
        } else {
            newUser = new User(0, username, password, email, Roles.USER);
        }

        session.setCurrentUser(newUser);
        System.out.println(
                "Registered account with user name "
                        + username
                        + "\nYou are now authenticated as "
                        + username);

        return newUser;
    }

    public String logout() {
        if (session.isAnonymous()) {
            return "You are not logged in.";
        }

        session.setCurrentUser(null);
        return "Successfully logged out.";
    }

    public User promote(List<User> users, String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                user.setRole(Roles.ADMINISTRATOR);
                return user;
            }
        }

        throw new InvalidCredentialsException("User not found. Cannot promote non-existent user.");
    }
}
