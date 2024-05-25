package authentication;

import exceptions.UserAlreadyExistsException;
import java.util.List;
import java.util.Scanner;
import tablesCreation.UsersCreation;
import users.Roles;
import users.User;

public class RegisterCommand extends AuthCommand {
    private final List<User> users;
    private final UsersCreation usersCreation;
    private final SessionManager sessionManager;

    public RegisterCommand(
            List<User> users, UsersCreation usersCreation, SessionManager sessionManager) {
        super(sessionManager);
        this.users = users;
        this.usersCreation = usersCreation;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute() {
        notRequireLoggedIn();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String email = scanner.nextLine();
        System.out.println("Enter email: ");
        String password = scanner.nextLine();

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

        sessionManager.setCurrentUser(newUser);
        System.out.println(
                "Registered account with user name "
                        + username
                        + "\nYou are now authenticated as "
                        + username);

        usersCreation.insertUser(newUser);
        users.add(newUser);
    }
}
