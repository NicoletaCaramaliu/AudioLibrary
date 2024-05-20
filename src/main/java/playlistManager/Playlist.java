package playlistManager;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private final int id;
    private final String name;
    private final int userId;
    private final List<Integer> songIds;

    public Playlist(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.songIds = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public List<Integer> getSongIds() {
        return songIds;
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
