import authentication.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pagination.Paginator;
import playlistManager.*;
import songsManager.CreateSong;
import songsManager.CreateSongCommand;
import songsManager.SearchCommand;
import songsManager.Song;
import tablesCreation.PlaylistCreation;
import tablesCreation.SongsCreation;
import tablesCreation.UsersCreation;
import authentication.User;

public class Main {
    public static void main(String[] args) {
        UsersCreation usersCreation = new UsersCreation();
        List<User> users = new ArrayList<>(UsersCreation.getAllUsers());
        for (User user : users) {
            System.out.println(user);
        }

        SongsCreation songsCreation = new SongsCreation();
        List<Song> songs = new ArrayList<>(SongsCreation.getAllSongs());

        PlaylistCreation playlistCreation = new PlaylistCreation();
        List<Playlist> playlists = new ArrayList<>(PlaylistCreation.getAllPlaylists());

        SessionManager session = new SessionManager();
        Authentication auth = new Authentication(session);
        CreateSong createSong = new CreateSong(session);
        CreatePlaylist createPlaylist = new CreatePlaylist(session);
        Scanner scanner = new Scanner(System.in);

        int itemsPerPage = 10;

        while (true) {
            System.out.print(
                    "Enter command: 1. login 2. register 3. logout 4. promote 5.view songs 6.create"
                            + " song 7.create playlist 8. list playlists 9. add song to playlist 10.search song 11.exit\n");
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
                    action = new CreateSongCommand(songs, createSong, songsCreation);
                    break;
                case "7":
                    action =
                            new CreatePlaylistCommand(
                                    playlists, createPlaylist, playlistCreation, session);
                    break;
                case "8":
                    if(session.getCurrentUser() == null) {
                        System.out.println("You need to be logged in to view your playlists.");
                        continue;
                    }
                    action =
                            new ListPlaylistCommand(
                                    playlists, itemsPerPage, session.getCurrentUser().getUserId());
                    break;
                case "9":
                    action = new AddSongToPlaylistCommand(playlists, songs, playlistCreation, songsCreation, session, scanner);
                    break;
                case "10":
                    if(session.getCurrentUser() == null) {
                        System.out.println("You need to be logged in to search songs.");
                        continue;
                    }
                    System.out.print("Enter search type (author/name): ");
                    String searchType = scanner.nextLine();
                    System.out.print("Enter search criteria: ");
                    String searchCriteria = scanner.nextLine();
                    action = new SearchCommand(songs, itemsPerPage, searchType, searchCriteria);
                    break;
                case "11":
                    return;
                default:
                    System.out.println("Unknown command.");
                    continue;
            }
            action.execute();
        }
    }
}
