package songsManager;

import authentication.SessionManager;
import java.util.List;
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
                        System.out.println("Song already exists in the library.");
                        return null;
                    }
                }
                Song newSong = new Song(songs.size() + 1, title, artist, releaseYear);
                songs.add(newSong);
                songsCreation.insertSong(newSong);
                System.out.println("Added " + title + " by " + artist + " to the library.");
                return newSong;
            } else {
                System.out.println("You need to be an admin to add a song");
            }
        } else {
            System.out.println("You need to be logged in to add a song");
            return null;
        }
        return null;
    }
}
