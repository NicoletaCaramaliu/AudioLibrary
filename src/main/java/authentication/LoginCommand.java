package authentication;

import java.util.List;
import java.util.Scanner;
import usersClasses.User;

public class LoginCommand implements Command {
    private final Authentication auth;
    private final List<User> users;
    private final Scanner scanner;

    public LoginCommand(Authentication auth, List<User> users, Scanner scanner) {
        this.auth = auth;
        this.users = users;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter username: ");
        String loginUsername = scanner.nextLine();
        System.out.println("Enter password: ");
        String loginPassword = scanner.nextLine();
        String loginResult = auth.login(users, loginUsername, loginPassword);
        System.out.println(loginResult);
    }
}
