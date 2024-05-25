package playlistManager;

import authentication.SessionManager;
import exceptions.InvalidInputException;
import exceptions.InvalidPlaylistException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import playlistRepository.Playlist;
import playlistRepository.PlaylistRepository;
import songRepository.Song;
import songRepository.SongRepository;
import tablesCreation.PlaylistCreation;

public class AddSongToPlaylistCommand extends PlaylistCommand {
    private final SongRepository songRepository;

    public AddSongToPlaylistCommand(
            SessionManager sessionManager,
            PlaylistCreation playlistCreation,
            PlaylistRepository playlistRepository,
            SongRepository songRepository) {
        super(sessionManager, playlistCreation, playlistRepository);
        this.songRepository = songRepository;
    }

    @Override
    public void execute() {
        requireLoggedIn();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter command (byName/byId): ");
        String method = scanner.next();

        if (!method.equals("byName") && !method.equals("byId")) {
            throw new InvalidInputException("Invalid method. Use 'byName' or 'byId'.");
        }

        System.out.print("Enter playlist identifier: ");
        String playlistIdentifier = scanner.next();
        scanner.nextLine();

        System.out.print("Enter song identifiers (separated by space): ");
        String[] songIdentifiers = scanner.nextLine().split(" ");

        if (method.equals("byName")) {
            addSongsToPlaylistByName(playlistIdentifier, songIdentifiers);
        } else {
            addSongsToPlaylistById(playlistIdentifier, songIdentifiers);
        }
    }

    private void addSongsToPlaylistByName(String playlistName, String[] songIdentifiers) {
        Playlist playlist =
                playlistRepository
                        .findByNameAndUserId(
                                playlistName, sessionManager.getCurrentUser().getUserId())
                        .orElseThrow(
                                () ->
                                        new InvalidPlaylistException(
                                                "The name of the desired playlist does not exist in"
                                                        + " your list."));

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

        Playlist playlist =
                playlistRepository
                        .findByIdAndUserId(id, sessionManager.getCurrentUser().getUserId())
                        .orElseThrow(
                                () ->
                                        new InvalidPlaylistException(
                                                "The id of the desired playlist does not exist in"
                                                        + " your list."));

        addSongsToPlaylist(playlist, songIdentifiers);
    }

    private void addSongsToPlaylist(Playlist playlist, String[] songIdentifiers) {
        List<Song> songs = songRepository.findAll();
        List<Integer> songIds = songs.stream().map(Song::songId).toList();

        List<Song> songsToAdd = songRepository.findByIdentifiers(songIdentifiers);

        List<String> invalidIdentifiers = new ArrayList<>();
        for (String identifier : songIdentifiers) {
            if (!songIds.contains(Integer.parseInt(identifier))) {
                invalidIdentifiers.add(identifier);
            }
        }

        if (!invalidIdentifiers.isEmpty()) {
            invalidIdentifiers.forEach(
                    name ->
                            System.out.println(
                                    "Song with identifier " + name + " does not exist."));
        }

        List<Song> existingSongs =
                songsToAdd.stream().filter(song -> playlist.containsSong(song.songId())).toList();

        songsToAdd =
                songsToAdd.stream().filter(song -> !playlist.containsSong(song.songId())).toList();

        if (!existingSongs.isEmpty()) {
            existingSongs.forEach(
                    song ->
                            System.out.println(
                                    "The song \""
                                            + song.title()
                                            + "\" by \""
                                            + song.artist()
                                            + "\" is already part of \""
                                            + playlist.getName()
                                            + "\"."));
        }

        if (!songsToAdd.isEmpty()) {
            songsToAdd.forEach(
                    song -> {
                        playlist.addSong(song);
                        playlistCreation.insertPlaylistSong(playlist.getId(), song.songId());
                        System.out.println(
                                "Added \""
                                        + song.title()
                                        + "\" by "
                                        + song.artist()
                                        + " to "
                                        + playlist.getName()
                                        + ".");
                    });
        }
    }
}
