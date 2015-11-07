package upmc.darapp.users.dao;

import upmc.darapp.users.model.User;

public interface UserDAO {
    void createNewUser(User u);
    void createNewUserRole(User u);
	User findByUserName(String username);
}