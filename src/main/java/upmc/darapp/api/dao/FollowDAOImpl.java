package upmc.darapp.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import upmc.darapp.api.model.Follow;

public class FollowDAOImpl implements FollowDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session = null;

    @Transactional
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public boolean testUserForEvent(String u, int e_id) {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Follow follow where follow.followed_event_id = :e_id and follow.user = :u");
        query.setParameter("e_id", e_id);
        query.setParameter("u", u);
        Follow f_test = (Follow) query.uniqueResult();
        if (f_test != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void addUserFollow(String u, int e_id) {
        session = sessionFactory.getCurrentSession();
        Follow f = new Follow(u, e_id);
        session.save(f);
        session.flush();
    }

    @Transactional
    public void removeUserFollow(String u, int e_id) {
        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from Follow follow where follow.followed_event_id = :e_id and follow.user = :u");
        query.setParameter("e_id", e_id);
        query.setParameter("u", u);
        query.executeUpdate();
        session.flush();
    }
}
