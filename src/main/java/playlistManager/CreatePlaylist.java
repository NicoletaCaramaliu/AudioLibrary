package playlistManager;

import authentication.SessionManager;
import java.util.List;
import tablesCreation.PlaylistCreation;

public class CreatePlaylist {
    private final SessionManager sessionManager;

    public CreatePlaylist(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Playlist addPlaylistToUser(
            List<Playlist> playlists, String name, PlaylistCreation playlistCreation) {

        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)
                    && playlist.getUserId() == sessionManager.getCurrentUser().getUserId()) {
                System.out.println("You already have a playlist named " + name);
                return null;
            }
        }
        Playlist newPlaylist =
                new Playlist(
                        playlists.size() + 1, name, sessionManager.getCurrentUser().getUserId());
        System.out.println("Added " + name + " to your playlists.");
        return newPlaylist;
    }
}
