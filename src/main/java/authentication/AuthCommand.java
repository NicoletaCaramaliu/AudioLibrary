package authentication;

import sessionVerification.BaseCommand;

public abstract class AuthCommand extends BaseCommand implements Command {
    protected final SessionManager sessionManager;

    public AuthCommand(SessionManager sessionManager) {
        super(sessionManager);
        this.sessionManager = sessionManager;
    }
}
