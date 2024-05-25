package playlistManager;

import authentication.Command;
import authentication.SessionManager;
import exceptions.InvalidSessionException;
import playlistRepository.PlaylistRepository;
import tablesCreation.PlaylistCreation;

public abstract class PlaylistCommand implements Command {
    protected final SessionManager sessionManager;
    protected final PlaylistCreation playlistCreation;
    protected final PlaylistRepository playlistRepository;

    public PlaylistCommand(
            SessionManager sessionManager,
            PlaylistCreation playlistCreation,
            PlaylistRepository playlistRepository) {
        this.sessionManager = sessionManager;
        this.playlistCreation = playlistCreation;
        this.playlistRepository = playlistRepository;
    }

    protected void requireLoggedIn() {
        if (sessionManager.getCurrentUser() == null) {
            throw new InvalidSessionException(
                    "You need to be logged in to perform this operation.");
        }
    }
}
