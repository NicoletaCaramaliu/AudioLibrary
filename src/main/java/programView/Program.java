package programView;

import auditManager.Audit;
import auditManager.AuditCommand;
import auditManager.AuditRepository;
import auditManager.InMemoryAuditRepository;
import authentication.*;
import com.google.gson.reflect.TypeToken;
import exceptions.*;
import exportPlaylist.ExportPlaylistCommand;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import playlistManager.AddSongToPlaylistCommand;
import playlistManager.CreatePlaylistCommand;
import playlistManager.ListPlaylistCommand;
import playlistRepository.InMemoryPlaylistRepository;
import playlistRepository.Playlist;
import playlistRepository.PlaylistRepository;
import readFileData.JsonUtility;
import readFileData.XmlUtility;
import songRepository.InMemorySongRepository;
import songRepository.Song;
import songRepository.SongRepository;
import songsManager.CreateSongCommand;
import songsManager.SearchCommand;
import tablesCreation.AuditCreation;
import tablesCreation.PlaylistCreation;
import tablesCreation.SongsCreation;
import tablesCreation.UsersCreation;
import users.User;

public class Program implements ProgramRunner {


    @Override
    public void start() {
        UsersCreation usersCreation = new UsersCreation();
        SessionManager session = new SessionManager();
        SongsCreation songsCreation = new SongsCreation();
        PlaylistCreation playlistCreation = new PlaylistCreation();
        PlaylistRepository playlistRepository = new InMemoryPlaylistRepository();
        SongRepository songRepository = new InMemorySongRepository();
        AuditRepository auditRepository = new InMemoryAuditRepository();

        List<Playlist> playlists = new ArrayList<>(PlaylistCreation.getAllPlaylists());
        List<Audit> audits = new ArrayList<>(AuditCreation.getAudits());

        //users from user file
        List<User> users = readUsersFromJsonFile("users.json");
        List<Song> songs = readSongsFromXmlFile("songs.xml");


        Scanner scanner = new Scanner(System.in);



        for (Playlist playlist : playlists) {
            playlistRepository.addPlaylist(playlist);
        }

        for (Song song : songs) {
            songRepository.save(song);
        }

        for (Audit audit : audits) {
            auditRepository.addAudit(audit);
        }

        while (true) {
            System.out.print(
                    """
                            Enter command:\s
                            1. login
                            2. register\s
                            3. logout\s
                            4. promote\s
                            5. create song\s
                            6. create playlist\s
                            7. list playlists\s
                            8. add song to playlist\s
                            9. search song\s
                            10. export playlist\s
                            11. audit
                            12. exit
                            """);
            String command = scanner.nextLine();

            Command action;
            int itemsPerPage = 10;
            switch (command) {
                case "1":
                    action = new LoginCommand(users, session);
                    break;
                case "2":
                    action = new RegisterCommand(users, usersCreation, session);
                    break;
                case "3":
                    action = new LogoutCommand(session);
                    break;
                case "4":
                    action = new PromoteCommand(users, session, usersCreation);
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
                    action =
                            new ExportPlaylistCommand(
                                    session.getCurrentUser().getUserId(),
                                    playlistCreation,
                                    session.getCurrentUser().getUsername(),
                                    session);
                    break;
                case "11":
                    action = new AuditCommand(auditRepository, session, itemsPerPage);
                    break;
                case "12":
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
                    | InvalidInputException
                    | InvalidExportFormatException e) {
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
    private List<User> readUsersFromJsonFile(String fileName) {
        List<User> users = new ArrayList<>();
        try {
            JsonUtility<User> jsonUserUtility = new JsonUtility<>(User.class);
            users = jsonUserUtility.readData(fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while reading data from file:" + e.getMessage());
        }
        return users;
    }

    private List<Song> readSongsFromXmlFile(String fileName) {
        List<Song> songs = new ArrayList<>();
        try {
            XmlUtility<Song> xmlSongUtility = new XmlUtility<>(Song.class);
            songs = xmlSongUtility.readData(fileName);
        } catch (IOException e) {
            System.out.println("Eroare la citirea melodiilor din fișierul XML: " + e.getMessage());
        }
        return songs;
    }

}
