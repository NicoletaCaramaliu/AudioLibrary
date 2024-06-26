package playlistRepository;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import songRepository.Song;

@Getter
public class Playlist {
    private final int id;
    @Getter private final String name;
    private final int userId;
    @Getter @Setter private List<Song> songs;

    public Playlist(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public boolean containsSong(int songId) {
        return songs.stream().anyMatch(song -> song.songId() == songId);
    }

    @Override
    public String toString() {
        return "Playlist{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", userId="
                + userId
                + ", songs="
                + songs
                + '}';
    }
}
