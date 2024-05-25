package songsManager;

import authentication.Command;
import authentication.SessionManager;
import sessionVerification.BaseCommand;
import songRepository.SongRepository;

public abstract class SongCommand extends BaseCommand implements Command {
    protected final SessionManager sessionManager;
    protected final SongRepository songRepository;

    public SongCommand(SessionManager sessionManager, SongRepository songRepository) {
        super(sessionManager);
        this.sessionManager = sessionManager;
        this.songRepository = songRepository;
    }
}
