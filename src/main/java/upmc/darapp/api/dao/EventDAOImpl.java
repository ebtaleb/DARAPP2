package upmc.darapp.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

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
        List<Event> ts = query.list();

        return ts;
    }

    @Transactional
    public List<Event> findUserOwnedEvents(String u) {
        session = sessionFactory.openSession();
        Query query = session.createQuery("from Event event where event.owner = :u");
        List<Event> ts = query.setParameter("u", u).list();

        return ts;
    }

    @Transactional
    public List<Event> findUserEventSubscriptions(String u) {
        session = sessionFactory.openSession();
        Query query = session.createQuery("select event from Event event, Follow follow where follow.user = :u and follow.followed_event_id = event.id");

        List<Event> ts = query.setParameter("u", u).list();
        return ts;
    }

    @Transactional
    public Event get(int id) {
        session = sessionFactory.openSession();
        Query query = session.createQuery("from Event where id = :id");
        query.setParameter("id", id);
        Event found = (Event) query.uniqueResult();

        return found;
    }

    @Transactional
    public void add(Event event) {
        session.save(event);
    }
}
