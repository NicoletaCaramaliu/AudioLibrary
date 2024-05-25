package authentication;

import exceptions.InvalidCredentialsException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import tablesCreation.UsersCreation;
import users.Roles;
import users.User;

public class PromoteCommand extends AuthCommand {
    private final List<User> users;
    private final UsersCreation usersCreation;

    public PromoteCommand(List<User> users, SessionManager session, UsersCreation usersCreation) {
        super(session);
        this.users = users;
        this.usersCreation = usersCreation;
    }

    @Override
    public void execute() {
        requireLoggedIn();
        requireAdmin();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine().trim();
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                if (user.getRole() == Roles.ADMINISTRATOR) {
                    throw new InvalidCredentialsException("User is already an administrator.");
                }
                user.setRole(Roles.ADMINISTRATOR);
                System.out.println("User " + username + " has been promoted to administrator.");
                usersCreation.updateUser(user);
                return;
            }
        }

        throw new InvalidCredentialsException("User not found. Cannot promote non-existent user.");
    }
}
