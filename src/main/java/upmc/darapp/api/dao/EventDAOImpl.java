package upmc.darapp.api.dao;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import upmc.darapp.api.model.Event;

public class EventDAOImpl implements EventDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session = null;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Event> getAll() {
        session = sessionFactory.openSession();
        Query query = session.createQuery("from Event");
        return query.list();
    }

    @Transactional
    public List<Event> findUserOwnedEvents(String u) {
        session = sessionFactory.openSession();
        Query query = session.createQuery("from Event event where event.owner = :u");
        return query.setParameter("u", u).list();
    }

    @Transactional
    public List<Event> findUserEventSubscriptions(String u) {
        session = sessionFactory.openSession();
        Query query = session.createQuery("select event from Event event, Follow follow where follow.user = :u and follow.followed_event_id = event.id");
        return query.setParameter("u", u).list();
    }

    @Transactional
    public Event get(int id) {
        session = sessionFactory.openSession();
        Query query = session.createQuery("from Event where id = :id");
        query.setParameter("id", id);
        return (Event) query.uniqueResult();
    }

    @Transactional
    public void add(Event event) {
        session = sessionFactory.openSession();
        session.save(event);
        session.flush();
    }
}
