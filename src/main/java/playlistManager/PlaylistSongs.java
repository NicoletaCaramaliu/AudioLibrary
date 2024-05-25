package playlistManager;

public class PlaylistSongs {
    private final int playlistId;
    private final int songId;

    public PlaylistSongs(int playlistId, int songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public int getSongId() {
        return songId;
    }
}
