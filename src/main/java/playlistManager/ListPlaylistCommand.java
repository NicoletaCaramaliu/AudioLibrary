package playlistManager;

import authentication.Command;
import java.util.List;
import java.util.stream.Collectors;
import pagination.Paginator;

public class ListPlaylistCommand implements Command {
    private List<Playlist> playlists;
    private int itemsPerPage;
    private int currentUserId;

    public ListPlaylistCommand(List<Playlist> playlists, int itemsPerPage, int userId) {
        this.playlists = playlists;
        this.itemsPerPage = itemsPerPage;
        this.currentUserId = userId;
    }

    @Override
    public void execute() {

        List<Playlist> userPlaylists =
                playlists.stream()
                        .filter(playlist -> playlist.getUserId() == currentUserId)
                        .collect(Collectors.toList());

        if (userPlaylists.isEmpty()) {
            System.out.println("You don't have any playlists.");
            return;
        }

        Paginator<Playlist> paginator = new Paginator<>(userPlaylists, itemsPerPage);
        paginator.paginate();
    }
}
