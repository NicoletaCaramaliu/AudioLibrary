package authentication;

import java.util.List;
import java.util.Scanner;
import tablesCreation.UsersCreation;

public class PromoteCommand implements Command {
    private final Authentication authentication;
    private final List<User> users;
    private final SessionManager session;
    private final UsersCreation usersCreation;

    public PromoteCommand(
            Authentication authentication,
            List<User> users,
            SessionManager session,
            UsersCreation usersCreation) {
        this.authentication = authentication;
        this.users = users;
        this.session = session;
        this.usersCreation = usersCreation;
    }

    @Override
    public void execute() {
        if (session.isAnonymous()) {
            System.out.println("You are not logged in.");
            return;
        }
        if (!session.isAdmin()) {
            System.out.println(
                    "You do not have permission to promote. You are not an administrator.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine().trim();
        User promotedUser = authentication.promote(users, username);
        if (promotedUser != null) {
            usersCreation.updateUser(promotedUser);
            System.out.println(username + " is now an administrator!");
        } else {
            System.out.println("Specified user does not exist!");
        }
    }
}
