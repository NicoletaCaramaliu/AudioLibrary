package songsManager;

import authentication.Command;
import authentication.SessionManager;
import exceptions.InvalidSessionException;
import songRepository.SongRepository;

public abstract class SongCommand implements Command {
    protected final SessionManager sessionManager;
    protected final SongRepository songRepository;

    public SongCommand(SessionManager sessionManager, SongRepository songRepository) {
        this.sessionManager = sessionManager;
        this.songRepository = songRepository;
    }

    protected void requireLoggedIn() {
        if (sessionManager.getCurrentUser() == null) {
            throw new InvalidSessionException(
                    "You need to be logged in to perform this operation.");
        }
    }

    protected void requireAdmin() {
        if (!sessionManager.isAdmin()) {
            throw new InvalidSessionException("You need to be an admin to perform this operation.");
        }
    }
}
