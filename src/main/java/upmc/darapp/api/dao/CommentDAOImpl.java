package upmc.darapp.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import upmc.darapp.api.model.Comment;

public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session = null;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Comment> getAllCommentsForEvent(int e_id) {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comment comment where comment.event_id = :e_id");
		List<Comment> result = query.setParameter("e_id", e_id).list();
        return result;
    }

    @Transactional
    public List<Comment> findUserOwnedComments(String u) {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comment comment where comment.owner = :user");
		List<Comment> result = query.setParameter("user", u).list();
        return result;
    }

    @Transactional
    public void add(Comment c) {
        session = sessionFactory.getCurrentSession();
        session.save(c);
        session.flush();
    }

}