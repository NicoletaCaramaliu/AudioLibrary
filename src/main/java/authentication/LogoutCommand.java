package authentication;

public class LogoutCommand extends AuthCommand {
    private final SessionManager sessionManager;

    public LogoutCommand(SessionManager sessionManager) {
        super(sessionManager);
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute() {
        requireLoggedIn();
        sessionManager.setCurrentUser(null);
        System.out.println("Successfully logged out.");
    }
}
