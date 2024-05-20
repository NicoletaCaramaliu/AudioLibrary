package authentication;

import lombok.Getter;
import lombok.Setter;
import usersClasses.User;

@Setter
@Getter
public class SessionManager {
    private User currentUser;

    public SessionManager() {
        this.currentUser = null;
    }

    public boolean isAnonymous() {
        return currentUser == null;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getIsAdministrator();
    }
}
