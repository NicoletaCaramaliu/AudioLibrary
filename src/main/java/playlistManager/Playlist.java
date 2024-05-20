package playlistManager;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    @Getter
    private final int id;
    @Getter
    private final String name;
    @Getter
    private final int userId;
    private final List<Integer> songIds;

    public Playlist(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.songIds = new ArrayList<>();
    }


    public void addSong(int songId) {
        songIds.add(songId);
    }

    public boolean containsSong(int songId) {
        return songIds.contains(songId);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", songIds=" + songIds +
                '}';
    }
}
