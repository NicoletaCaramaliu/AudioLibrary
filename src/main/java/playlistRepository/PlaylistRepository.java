package playlistRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository {
    void addPlaylist(Playlist playlist);

    List<Playlist> getAllPlaylists();

    // get all playlists by user id
    List<Playlist> getAllPlaylistsByUserId(int userId);

    Optional<Playlist> findByNameAndUserId(String name, int userId);

    // find playlist by id
    Optional<Playlist> findByIdAndUserId(int id, int userId);

    // playlist already exists
    boolean playlistExists(String name, int userId);
}
