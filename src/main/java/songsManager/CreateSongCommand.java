package songsManager;

import authentication.Command;
import java.util.List;
import java.util.Scanner;

import exceptions.InvalidSessionException;
import exceptions.InvalidSongException;
import tablesCreation.SongsCreation;

public class CreateSongCommand implements Command {
    private final List<Song> songs;
    private final CreateSong createSong;
    private final SongsCreation songsCreation;

    public CreateSongCommand(List<Song> songs, CreateSong createSong, SongsCreation songsCreation) {
        this.songs = songs;
        this.createSong = createSong;
        this.songsCreation = songsCreation;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter song name: ");
        String songName = scanner.nextLine();
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        System.out.print("Enter release year: ");
        int releaseYear = Integer.parseInt(scanner.nextLine());
        try {
            Song newSong =
                    createSong.addSongToLibrary(songs, songName, artist, releaseYear, songsCreation);
                songs.add(newSong);
                songsCreation.insertSong(newSong);
        } catch (InvalidSongException | InvalidSessionException e) {
            System.out.println(e.getMessage());
        }
    }
}
