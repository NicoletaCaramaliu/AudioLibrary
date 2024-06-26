package songRepository;

public record Song(int songId, String title, String artist, int releaseYear) {
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
