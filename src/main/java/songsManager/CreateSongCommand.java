package songsManager;

import authentication.SessionManager;
import exceptions.InvalidSongException;
import java.util.List;
import java.util.Scanner;
import songRepository.Song;
import songRepository.SongRepository;
import tablesCreation.SongsCreation;

public class CreateSongCommand extends SongCommand {
    private final SongsCreation songsCreation;

    public CreateSongCommand(
            SessionManager sessionManager, SongRepository repository, SongsCreation songsCreation) {
        super(sessionManager, repository);
        this.songsCreation = songsCreation;
    }

    @Override
    public void execute() {

        requireLoggedIn();
        requireAdmin();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter song name: ");
        String songName = scanner.nextLine();
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        System.out.print("Enter release year: ");
        int releaseYear = Integer.parseInt(scanner.nextLine());

        List<Song> existingSongs = songRepository.findByTitleAndArtist(songName, artist);
        if (!existingSongs.isEmpty()) {
            throw new InvalidSongException("Song already exists in the library.");
        }
        int songId = songRepository.findAll().size() + 1;
        Song newSong = new Song(songId, songName, artist, releaseYear);
        songRepository.save(newSong);
        songsCreation.insertSong(newSong);
        System.out.println("Added " + songName + " by " + artist + " to the library.");
    }
}
