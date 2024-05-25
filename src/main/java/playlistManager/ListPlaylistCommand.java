package playlistManager;

import authentication.SessionManager;
import java.util.List;
import pagination.Paginator;
import playlistRepository.Playlist;
import playlistRepository.PlaylistRepository;
import tablesCreation.PlaylistCreation;

public class ListPlaylistCommand extends PlaylistCommand {
    private final int itemsPerPage;

    public ListPlaylistCommand(
            int itemsPerPage,
            SessionManager sessionManager,
            PlaylistCreation playlistCreation,
            PlaylistRepository playlistRepository) {
        super(sessionManager, playlistCreation, playlistRepository);
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public void execute() {
        requireLoggedIn();

        List<Playlist> userPlaylists =
                playlistRepository.getAllPlaylistsByUserId(
                        sessionManager.getCurrentUser().getUserId());

        if (userPlaylists.isEmpty()) {
            System.out.println("You don't have any playlists.");
            return;
        }

        Paginator<Playlist> paginator = new Paginator<>(userPlaylists, itemsPerPage);
        paginator.paginate();
    }
}
