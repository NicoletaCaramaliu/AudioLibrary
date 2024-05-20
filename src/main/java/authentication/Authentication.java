package authentication;

import java.util.List;
import java.util.Objects;
import usersClasses.User;

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

        return "Username or password is invalid. Please try again!";
    }

    public User register(List<User> users, String username, String password, String email) {
        if (users.isEmpty()) {
            // Daca baza de date este goala, setam utilizatorul nou ca administrator
            User newUser = new User(0, username, password, email, true); // Setare ca administrator
            session.setCurrentUser(newUser); // Setare utilizator curent in sesiune
            System.out.println(
                    "Registered account with user name "
                            + username
                            + "\nYou are now authenticated as "
                            + username);
            return newUser;
        } else {

            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return null;
                }
            }

            // Register the new user
            User newUser = new User(0, username, password, email, false);
            session.setCurrentUser(newUser);
            System.out.println(
                    "Registered account with user name "
                            + username
                            + "\nYou are now authenticated as "
                            + username);

            return newUser;
        }
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
                user.setAdministrator(true);
                return user;
            }
        }

        return null;
    }
}
