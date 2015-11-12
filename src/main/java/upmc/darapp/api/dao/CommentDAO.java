package upmc.darapp.api.dao;

import upmc.darapp.api.model.Comment;
import java.util.List;

public interface CommentDAO {
    List<Comment> getAllCommentsForEvent(int e_id);
    List<Comment> findUserOwnedComments(String u);
    void add(Comment c);
}