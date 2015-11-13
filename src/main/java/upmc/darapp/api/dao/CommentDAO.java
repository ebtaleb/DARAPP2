package upmc.darapp.api.dao;

import java.util.List;

import upmc.darapp.api.model.Comment;

public interface CommentDAO {
    List<Comment> getAllCommentsForEvent(int e_id);
    List<Comment> findUserOwnedComments(String u);
    void add(Comment c);
}