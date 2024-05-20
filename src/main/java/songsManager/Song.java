package songsManager;

import lombok.Getter;

@Getter
public class Song {
    private final int songId;
    private final String title;
    private final String artist;
    private final int releaseYear;

    public Song(int songId, String title, String artist, int releaseYear) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Song{"
                + "songId="
                + songId
                + ", title='"
                + title
                + '\''
                + ", artist='"
                + artist
                + '\''
                + ", releaseYear="
                + releaseYear
                + '}';
    }
}
