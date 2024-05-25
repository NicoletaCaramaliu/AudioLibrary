package authentication;

import exceptions.InvalidCredentialsException;
import java.util.List;
import java.util.Scanner;
import users.User;

public class LoginCommand extends AuthCommand {
    private final List<User> users;
    private final SessionManager sessionManager;

    public LoginCommand(List<User> users, SessionManager sessionManager) {
        super(sessionManager);
        this.users = users;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute() {
        notRequireLoggedIn();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String loginUsername = scanner.nextLine();
        System.out.println("Enter password: ");
        String loginPassword = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(loginUsername)
                    && user.getPassword().equals(loginPassword)) {
                sessionManager.setCurrentUser(user);
                System.out.println("You are now authenticated as " + loginUsername);
                return;
            }
        }

        throw new InvalidCredentialsException("Username or password is invalid. Please try again!");
    }
}
