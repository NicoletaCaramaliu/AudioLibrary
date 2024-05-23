package songsManager;

import authentication.SessionManager;
import java.util.List;

import exceptions.InvalidSongException;
import tablesCreation.SongsCreation;

public class CreateSong {
    private final SessionManager sessionManager;

    public CreateSong(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Song addSongToLibrary(
            List<Song> songs,
            String title,
            String artist,
            int releaseYear,
            SongsCreation songsCreation) {
        if (!sessionManager.isAnonymous()) {
            if (sessionManager.isAdmin()) {
                for (Song song : songs) {
                    if (song.title().equals(title) && song.artist().equals(artist)) {
                        throw new InvalidSongException("Song already exists in the library.");
                    }
                }
                Song newSong = new Song(songs.size() + 1, title, artist, releaseYear);
                System.out.println("Added " + title + " by " + artist + " to the library.");
                return newSong;
            } else {
                throw new InvalidSongException("You need to be an admin to add a song");
            }
        } else {
            throw new InvalidSongException("You need to be logged in to add a song");
        }
    }
}
