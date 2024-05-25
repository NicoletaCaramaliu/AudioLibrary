package playlistManager;

import authentication.Command;
import authentication.SessionManager;
import playlistRepository.PlaylistRepository;
import sessionVerification.BaseCommand;
import tablesCreation.PlaylistCreation;

public abstract class PlaylistCommand extends BaseCommand implements Command {
    protected final SessionManager sessionManager;
    protected final PlaylistCreation playlistCreation;
    protected final PlaylistRepository playlistRepository;

    public PlaylistCommand(
            SessionManager sessionManager,
            PlaylistCreation playlistCreation,
            PlaylistRepository playlistRepository) {
        super(sessionManager);
        this.sessionManager = sessionManager;
        this.playlistCreation = playlistCreation;
        this.playlistRepository = playlistRepository;
    }
}
