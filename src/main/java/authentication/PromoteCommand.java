package authentication;

import exceptions.InvalidCredentialsException;
import java.util.List;
import java.util.Scanner;

import exceptions.InvalidSessionException;
import tablesCreation.AuditCreation;
import tablesCreation.UsersCreation;
import users.User;

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
            throw new InvalidSessionException("You are not logged in.");

        }
        if (!session.isAdmin()) {
            AuditCreation.insertAudit(session.getCurrentUser().getUsername(), "promote", false);
            throw new InvalidSessionException("You are not an administrator.");
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine().trim();
        User promotedUser = authentication.promote(users, username);
        usersCreation.updateUser(promotedUser);
        System.out.println(username + " is now an administrator!");

    }
}
