package playlistManager;

import lombok.Getter;

@Getter
public class PlaylistSongs {
    private final int playlistId;
    private final int songId;

    public PlaylistSongs(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }
}
