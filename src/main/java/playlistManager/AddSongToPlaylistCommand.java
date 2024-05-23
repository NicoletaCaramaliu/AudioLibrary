package playlistManager;

import authentication.Command;
import authentication.SessionManager;
import exceptions.InvalidPlaylistException;
import songsManager.Song;
import tablesCreation.PlaylistCreation;
import tablesCreation.SongsCreation;

import java.util.List;
import java.util.Scanner;

public class AddSongToPlaylistCommand implements Command{
    private final List<Playlist> playlists;
    private final List<Song> songs;
    private final PlaylistCreation playlistCreation;
    private final SessionManager sessionManager;
    private final Scanner scanner;

    public AddSongToPlaylistCommand(List<Playlist> playlists, List<Song> songs, PlaylistCreation playlistCreation, SongsCreation songsCreation, SessionManager sessionManager, Scanner scanner) {
        this.playlists = playlists;
        this.songs = songs;
        this.playlistCreation = playlistCreation;
        this.sessionManager = sessionManager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        if (sessionManager.getCurrentUser() == null) {
            System.out.println("You need to be logged in to add songs to a playlist.");
            return;
        }

        System.out.print("Enter command (byName/byId): ");
        String method = scanner.next();

        if (!method.equals("byName") && !method.equals("byId")) {
            System.out.println("Invalid method. Use 'byName' or 'byId'.");
            return;
        }

        System.out.print("Enter playlist identifier: ");
        String playlistIdentifier = scanner.next();
        scanner.nextLine();

        System.out.print("Enter song identifiers (separated by space): ");
        String[] songIdentifiers = scanner.nextLine().split(" ");

        if (method.equals("byName")) {
            try {
                addSongsToPlaylistByName(playlistIdentifier, songIdentifiers);
            } catch (InvalidPlaylistException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                addSongsToPlaylistById(playlistIdentifier, songIdentifiers);
            } catch (InvalidPlaylistException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addSongsToPlaylistByName(String playlistName, String[] songIdentifiers) {
        Playlist playlist = playlists.stream()
                .filter(p -> p.getName().equals(playlistName) && p.getUserId() == sessionManager.getCurrentUser().getUserId())
                .findFirst()
                .orElseThrow(() -> new InvalidPlaylistException("The name of the desired playlist does not exist."));

        addSongsToPlaylist(playlist, songIdentifiers);
    }

    private void addSongsToPlaylistById(String playlistId, String[] songIdentifiers) {
        int id;
        try {
            id = Integer.parseInt(playlistId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid playlist ID.");
            return;
        }

        Playlist playlist = playlists.stream()
                .filter(p -> p.getId() == id && p.getUserId() == sessionManager.getCurrentUser().getUserId())
                .findFirst()
                .orElseThrow(() -> new InvalidPlaylistException("The id of the desired playlist does not exist."));

        addSongsToPlaylist(playlist, songIdentifiers);
    }

    private void addSongsToPlaylist(Playlist playlist, String[] songIdentifiers) {
        List<Integer> songIds = songs.stream()
                .map(Song::songId)
                .toList();

        List<Song> songsToAdd = songs.stream()
                .filter(song -> {
                    for (String id : songIdentifiers) {
                        if (song.songId() == Integer.parseInt(id)) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();

        List<String> invalidSongs = songsToAdd.stream()
                .filter(song -> !songIds.contains(song.songId()))
                .map(Song::title)
                .toList();

        if (!invalidSongs.isEmpty()) {
            invalidSongs.forEach(name -> System.out.println("Song with identifier " + name + " does not exist."));
            return;
        }

        List<Song> existingSongs = songsToAdd.stream()
                .filter(song -> playlist.containsSong(song.songId()))
                .toList();

        if (!existingSongs.isEmpty()) {
            existingSongs.forEach(song -> System.out.println("The song \"" + song.title() + "\" by \"" + song.artist() + "\" is already part of \"" + playlist.getName() + "\"."));
            return;
        }

        songsToAdd.forEach(song -> {
            playlist.addSong(song);
            playlistCreation.insertPlaylistSong(playlist.getId(), song.songId());
            System.out.println("Added \"" + song.title() + "\" by " + song.artist() + " to " + playlist.getName() + ".");
        });
    }

}