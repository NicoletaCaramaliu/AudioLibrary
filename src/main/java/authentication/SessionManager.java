package authentication;

import UsersClasses.User;
import lombok.Getter;
import lombok.Setter;

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
