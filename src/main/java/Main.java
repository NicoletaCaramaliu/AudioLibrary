import authentication.*;
import exceptions.*;
import exportPlaylist.ExportFormat;
import exportPlaylist.ExportPlaylistCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import playlistManager.*;
import playlistRepository.InMemoryPlaylistRepository;
import playlistRepository.Playlist;
import playlistRepository.PlaylistRepository;
import songRepository.InMemorySongRepository;
import songRepository.Song;
import songRepository.SongRepository;
import songsManager.*;
import tablesCreation.AuditCreation;
import tablesCreation.PlaylistCreation;
import tablesCreation.SongsCreation;
import tablesCreation.UsersCreation;
import users.User;

public class Main {
    public static void main(String[] args) {
        UsersCreation usersCreation = new UsersCreation();
        List<User> users = new ArrayList<>(UsersCreation.getAllUsers());

        SessionManager session = new SessionManager();
        Authentication auth = new Authentication(session);

        PlaylistRepository playlistRepository = new InMemoryPlaylistRepository();
        List<Playlist> playlists = new ArrayList<>(PlaylistCreation.getAllPlaylists());
        for (Playlist playlist : playlists) {
            playlistRepository.addPlaylist(playlist);
        }

        SongsCreation songsCreation = new SongsCreation();
        List<Song> songs = new ArrayList<>(SongsCreation.getAllSongs());
        SongRepository songRepository = new InMemorySongRepository();
        for (Song song : songs) {
            songRepository.save(song);
        }

        PlaylistCreation playlistCreation = new PlaylistCreation();
        Scanner scanner = new Scanner(System.in);

        int itemsPerPage = 10;

        while (true) {
            System.out.print(
                    "Enter command: \n"
                            + "1. login\n"
                            + "2. register \n"
                            + "3. logout \n"
                            + "4. promote \n"
                            + "5. create song \n"
                            + "6. create playlist \n"
                            + "7. list playlists \n"
                            + "8. add song to playlist \n"
                            + "9. search song \n"
                            + "10. export playlist \n"
                            + "11. exit\n");
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
                    action = new CreateSongCommand(session, songRepository, songsCreation);
                    break;
                case "6":
                    action =
                            new CreatePlaylistCommand(
                                    session, playlistCreation, playlistRepository);
                    break;
                case "7":
                    action =
                            new ListPlaylistCommand(
                                    itemsPerPage, session, playlistCreation, playlistRepository);
                    break;
                case "8":
                    action =
                            new AddSongToPlaylistCommand(
                                    session, playlistCreation, playlistRepository, songRepository);
                    break;
                case "9":
                    action = new SearchCommand(session, songRepository, itemsPerPage);
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
                    action =
                            new ExportPlaylistCommand(
                                    playlistIdentifier,
                                    format,
                                    session.getCurrentUser().getUserId(),
                                    playlistCreation,
                                    session.getCurrentUser().getUsername());
                    break;
                case "11":
                    return;
                default:
                    System.out.println("Unknown command.");
                    continue;
            }
            try {
                String commandName = action.getClass().getSimpleName();
                action.execute();
                AuditCreation.insertAudit(
                        session.getCurrentUser() != null
                                ? session.getCurrentUser().getUsername()
                                : "anonymous",
                        commandName,
                        true);
            } catch (IllegalArgumentException
                    | InvalidCredentialsException
                    | UserAlreadyExistsException
                    | InvalidSessionException
                    | InvalidPlaylistException
                    | InvalidSongException
                    | InvalidInputException e) {
                AuditCreation.insertAudit(
                        session.getCurrentUser() != null
                                ? session.getCurrentUser().getUsername()
                                : "anonymous",
                        action.getClass().getSimpleName(),
                        false);
                System.out.println(e.getMessage());
            }
        }
    }
}
