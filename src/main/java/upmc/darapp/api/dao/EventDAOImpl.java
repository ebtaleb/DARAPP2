package upmc.darapp.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import upmc.darapp.api.model.Event;

public class EventDAOImpl implements EventDAO {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session = null;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Event> getAll() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Event");

        List<Event> ts = query.list();

        session.getTransaction().commit();
        session.close();

        return ts;
    }

    public Event get(int id) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Event where id = :id");
        query.setParameter("id", id);
        Event found = (Event) query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return found;
    }

    public void add(Event event) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(event);

        session.getTransaction().commit();
        session.close();
    }

    public void update(Event event) {

    }

    public void delete(int id) {

    }
}
