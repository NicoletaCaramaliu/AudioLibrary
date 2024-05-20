package playlistManager;

import authentication.Command;
import authentication.SessionManager;
import java.util.List;
import java.util.Scanner;
import tablesCreation.PlaylistCreation;

public class CreatePlaylistCommand implements Command {
    private final List<Playlist> playlists;
    private final CreatePlaylist createPlaylist;
    private final PlaylistCreation playlistCreation;
    private final SessionManager sessionManager;

    public CreatePlaylistCommand(
            List<Playlist> playlists,
            CreatePlaylist createPlaylist,
            PlaylistCreation playlistCreation,
            SessionManager sessionManager) {
        this.playlists = playlists;
        this.createPlaylist = createPlaylist;
        this.playlistCreation = playlistCreation;
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute() {
        if (sessionManager.getCurrentUser() == null) {
            System.out.println("You need to be logged in to create a playlist.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter playlist name: ");
        String playlistName = scanner.nextLine();
        Playlist newPlaylist =
                createPlaylist.addPlaylistToUser(playlists, playlistName);
        if (newPlaylist != null) {
            playlists.add(newPlaylist);
            playlistCreation.insertPlaylist(newPlaylist);
        }
    }
}
