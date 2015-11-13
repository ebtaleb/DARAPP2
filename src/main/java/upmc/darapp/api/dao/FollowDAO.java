package upmc.darapp.api.dao;

import java.util.List;

public interface FollowDAO {
    boolean testUserForEvent(String u, int e_id);
    void addUserFollow(String u, int e_id);
    void removeUserFollow(String u, int e_id);
}