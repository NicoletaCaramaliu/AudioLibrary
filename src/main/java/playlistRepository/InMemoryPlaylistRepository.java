package playlistRepository;

import java.util.*;
import songRepository.Song;

public class InMemoryPlaylistRepository implements PlaylistRepository {
    private final List<Playlist> playlists = new ArrayList<>();

    @Override
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylists() {
        return new ArrayList<>(playlists);
    }

    // get all playlists by user id
    @Override
    public List<Playlist> getAllPlaylistsByUserId(int userId) {
        return playlists.stream().filter(playlist -> playlist.getUserId() == userId).toList();
    }

    @Override
    public Optional<Playlist> findByNameAndUserId(String name, int userId) {
        return playlists.stream()
                .filter(
                        playlist ->
                                playlist.getName().equals(name) && playlist.getUserId() == userId)
                .findFirst();
    }

    // find playlist by id
    @Override
    public Optional<Playlist> findByIdAndUserId(int id, int userId) {
        return playlists.stream()
                .filter(playlist -> playlist.getId() == id && playlist.getUserId() == userId)
                .findFirst()
                .map(
                        playlist -> {
                            List<Song> songsInPlaylist = playlist.getSongs();
                            playlist.setSongs(songsInPlaylist);
                            return playlist;
                        });
    }

    // playlist already exists
    @Override
    public boolean playlistExists(String name, int userId) {
        return playlists.stream()
                .anyMatch(
                        playlist ->
                                playlist.getName().equals(name) && playlist.getUserId() == userId);
    }
}
