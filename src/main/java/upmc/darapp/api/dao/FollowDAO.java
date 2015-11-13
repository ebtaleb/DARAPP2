package upmc.darapp.api.dao;

import java.util.List;

public interface FollowDAO {
    boolean testUserForEvent(String u, int e_id);
    List<String> getAllUsersForEvent(int e_id);
    List<Integer> getAllEventsofUser(String u);
    void addUserFollow(String u, int e_id);
    void removeUserFollow(String u, int e_id);
}