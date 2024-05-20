package playlistManager;

import authentication.Command;
import java.util.List;
import java.util.stream.Collectors;
import pagination.Paginator;

public class ListPlaylistCommand implements Command {
    private final List<Playlist> playlists;
    private final int itemsPerPage;
    private final int currentUserId;

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
