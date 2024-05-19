package usersClasses;

import pagination.Pagination;
import tablesCreation.UsersCreation;

import java.util.List;

public class UserService {
    private final UsersCreation usersCreation;

    public UserService(UsersCreation usersCreation) {
        this.usersCreation = usersCreation;
    }

    public List<User> getUsers(int pageNumber, int itemsPerPage) {
        List<User> allUsers = UsersCreation.getAllUsers();
        return Pagination.getPage(allUsers, pageNumber, itemsPerPage);
    }
}
