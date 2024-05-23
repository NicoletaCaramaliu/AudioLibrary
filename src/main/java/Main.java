import authentication.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exportPlaylist.ExportFormat;
import exportPlaylist.ExportPlaylistCommand;
import pagination.Paginator;
import playlistManager.*;
import songsManager.CreateSong;
import songsManager.CreateSongCommand;
import songsManager.SearchCommand;
import songsManager.Song;
import tablesCreation.PlaylistCreation;
import tablesCreation.SongsCreation;
import tablesCreation.UsersCreation;
import users.User;

public class Main {
    public static void main(String[] args) {
        UsersCreation usersCreation = new UsersCreation();
        List<User> users = new ArrayList<>(UsersCreation.getAllUsers());

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
                    "Enter command: \n1. login\n2. register \n3. logout \n4. promote \n5. create"
                            + " song \n6. create playlist \n7. list playlists \n8. add song to playlist \n9. search song \n10. export playlist \n11. exit\n");
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
                    action = new CreateSongCommand(songs, createSong, songsCreation);
                    break;
                case "6":
                    action =
                            new CreatePlaylistCommand(
                                    playlists, createPlaylist, playlistCreation, session);
                    break;
                case "7":
                    if (session.getCurrentUser() == null) {
                        System.out.println("You need to be logged in to view your playlists.");
                        continue;
                    }
                    action = new ListPlaylistCommand(itemsPerPage, session.getCurrentUser().getUserId(), playlistCreation);
                    break;
                case "8":
                    action = new AddSongToPlaylistCommand(playlists, songs, playlistCreation, songsCreation, session, scanner);
                    break;
                case "9":
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
                case "10":
                    if (session.getCurrentUser() == null) {
                        System.out.println("You need to be logged in to export playlists.");
                        continue;
                    }
                    System.out.print("Enter playlist name or ID: ");
                    String playlistIdentifier = scanner.nextLine();
                    System.out.print("Enter export format (CSV/JSON): ");
                    String formatString = scanner.nextLine().toUpperCase();
                    ExportFormat format;
                    try {
                        format = ExportFormat.valueOf(formatString);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid format specified. Please enter CSV or JSON.");
                        continue;
                    }
                    action = new ExportPlaylistCommand(playlistIdentifier, format, session.getCurrentUser().getUserId(), playlistCreation, session.getCurrentUser().getUsername());
                    break;
                case "11":
                    return;
                default:
                    System.out.println("Unknown command.");
                    continue;
            }
            try {
                action.execute();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid usage of command " + command);
            }
        }
    }
}
