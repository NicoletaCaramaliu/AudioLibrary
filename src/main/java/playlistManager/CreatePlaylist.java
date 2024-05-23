package playlistManager;

import authentication.SessionManager;
import exceptions.InvalidPlaylistException;

import java.util.List;

public class CreatePlaylist {
    private final SessionManager sessionManager;

    public CreatePlaylist(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Playlist addPlaylistToUser(
            List<Playlist> playlists, String name) {

        for (Playlist playlist : playlists) {
            if (playlist.getName().equals(name)
                    && playlist.getUserId() == sessionManager.getCurrentUser().getUserId()) {
                throw new InvalidPlaylistException("Playlist with name " + name + " already exists.");
            }
        }
        Playlist newPlaylist =
                new Playlist(
                        playlists.size() + 1, name, sessionManager.getCurrentUser().getUserId());
        System.out.println("Added " + name + " to your playlists.");
        return newPlaylist;
    }
}
