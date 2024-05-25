package sessionVerification;

import authentication.SessionManager;
import exceptions.InvalidSessionException;

public abstract class BaseCommand {
    protected final SessionManager sessionManager;

    protected BaseCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
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

    protected void notRequireLoggedIn() {
        if (sessionManager.getCurrentUser() != null) {
            throw new InvalidSessionException(
                    "You need to be logged out to perform this operation.");
        }
    }
}
