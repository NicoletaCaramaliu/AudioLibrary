package playlistManager;

import authentication.Command;
import pagination.Paginator;
import tablesCreation.PlaylistCreation;

import java.util.List;

public class ListPlaylistCommand implements Command {
    private final int itemsPerPage;
    private final int currentUserId;
    private final PlaylistCreation playlistCreation;

    public ListPlaylistCommand(int itemsPerPage, int userId, PlaylistCreation playlistCreation) {
        this.itemsPerPage = itemsPerPage;
        this.currentUserId = userId;
        this.playlistCreation = playlistCreation;
    }

    @Override
    public void execute() {
        List<Playlist> userPlaylists = playlistCreation.getUserPlaylists(currentUserId);

        if (userPlaylists.isEmpty()) {
            System.out.println("You don't have any playlists.");
            return;
        }

        // Afisarea paginată a playlisturilor
        Paginator<Playlist> paginator = new Paginator<>(userPlaylists, itemsPerPage);
        paginator.paginate();
    }
}
