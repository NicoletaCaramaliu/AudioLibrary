import pagination.Pagination;
import pagination.Paginator;
import songsManager.Song;
import tablesCreation.SongsCreation;
import usersClasses.User;
import authentication.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tablesCreation.UsersCreation;

public class Main {
    public static void main(String[] args) {
        UsersCreation usersCreation = new UsersCreation();
        List<User> users = new ArrayList<>(UsersCreation.getAllUsers());

        SongsCreation songsCreation = new SongsCreation();
        List<Song> songs = new ArrayList<>(SongsCreation.getAllSongs());

        SessionManager session = new SessionManager();
        Authentication auth = new Authentication(session);
        Scanner scanner = new Scanner(System.in);

        int itemsPerPage = 10;

        while (true) {
            System.out.print("Enter command: 1. login 2. register 3. logout 4. promote 5.view users 6.exit\n");
            String command = scanner.nextLine();

            Command action;
            switch (command) {
                case "1":
                    action = new LoginCommand(auth, users, scanner);
                    break;
                case "2":
                    action = new RegisterCommand(auth, users, usersCreation);
                    break;
                case "3":
                    action = new LogoutCommand(auth);
                    break;
                case "4":
                    action = new PromoteCommand(auth, users, session, usersCreation);
                    break;
                case "5":
                    Paginator<Song> paginator = new Paginator<>(songs, itemsPerPage);
                    paginator.paginate();
                case "6":
                    return;
                default:
                    System.out.println("Unknown command.");
                    continue;
            }
            action.execute();
        }

    }
}