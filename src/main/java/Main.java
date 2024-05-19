import UsersClasses.User;
import authentication.Authentication;
import authentication.SessionManager;
import java.util.List;
import java.util.Scanner;
import tablesCreation.UsersCreation;

public class Main {
    public static void main(String[] args) {
        UsersCreation usersCreation = new UsersCreation();
        List<User> users = UsersCreation.getAllUsers();

        SessionManager session = new SessionManager();
        Authentication auth = new Authentication(session);
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print(
                    "Enter command: 1. login" + " 2. register" + " 3. logout" + " 4. promote\n");

            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    String loginResult = auth.login(users, loginUsername, loginPassword);
                    System.out.println(loginResult);
                    break;

                case "2":
                    if (!session.isAnonymous()) {
                        System.out.println("You are already logged in.");
                        break;
                    }
                    System.out.println("Enter username: ");
                    String registerUsername = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String registerPassword = scanner.nextLine();
                    User newUser = auth.register(users, registerUsername, registerPassword);
                    if (newUser != null) {
                        usersCreation.insertUser(newUser);
                        users.add(newUser);
                    }
                    break;

                case "3":
                    String logoutResult = auth.logout();
                    System.out.println(logoutResult);
                    break;

                case "4":
                    if (session.isAnonymous()) {
                        System.out.println("You are not logged in.");
                        break;
                    }
                    if (!session.isAdmin()) {
                        System.out.println(
                                "You do not have permission to promote. You are not an"
                                        + " administrator.");
                        break;
                    }
                    System.out.println("Enter username: ");
                    String promoteUsername = scanner.nextLine().trim();
                    User promotedUser = auth.promote(users, promoteUsername);
                    if (promotedUser != null) {
                        usersCreation.updateUser(promotedUser);
                        System.out.println(promoteUsername + " is now an administrator!");
                    } else {
                        System.out.println("Specified user does not exist!");
                    }
                    break;

                default:
                    System.out.println("Unknown command.");
            }
        }
    }
}
