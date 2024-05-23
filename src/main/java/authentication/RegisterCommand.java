package authentication;

import java.util.List;
import java.util.Scanner;

import users.User;
import exceptions.UserAlreadyExistsException;
import tablesCreation.UsersCreation;

public class RegisterCommand implements Command {
    private final Authentication authentication;
    private final List<User> users;
    private final UsersCreation usersCreation;

    public RegisterCommand(
            Authentication authentication, List<User> users, UsersCreation usersCreation) {
        this.authentication = authentication;
        this.users = users;
        this.usersCreation = usersCreation;
    }

    @Override
    public void execute() {
        if (!authentication.session().isAnonymous()) {
            System.out.println("You are already logged in.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password: ");
        String email = scanner.nextLine();
        System.out.println("Enter email: ");
        String password = scanner.nextLine();
        try {
            User newUser = authentication.register(users, username, password, email);
            usersCreation.insertUser(newUser);
            users.add(newUser);
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }
}
