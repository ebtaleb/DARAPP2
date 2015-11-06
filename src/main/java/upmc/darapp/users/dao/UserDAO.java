package upmc.darapp.users.dao;

import upmc.darapp.users.model.User;

public interface UserDAO {
	User findByUserName(String username);
}