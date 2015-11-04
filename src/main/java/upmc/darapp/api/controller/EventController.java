package upmc.darapp.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

import upmc.darapp.api.model.Event;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    SessionFactory sessionFactory;

    Session session = null;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
        public List<Event> getAllEvents() {

            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Event");

            List<Event> ts = query.list();

            session.getTransaction().commit();
            session.close();

            return ts;
        }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
        public Event fetchBy(@PathVariable("id") int id) {

            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Event where id=" + id);
            Event found = (Event) query.uniqueResult();

            session.getTransaction().commit();
            session.close();

            return found;
        }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
        public void createEvent(@RequestBody Event event, HttpServletResponse response) throws IOException {

            session = sessionFactory.openSession();
            session.beginTransaction();

            String result = "Event saved : " + event;
            session.save(event);

            session.getTransaction().commit();
            session.close();
            PrintWriter out = response.getWriter();
            out.println(result);
        }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
        public void updateEvent(@RequestBody Event event, HttpServletResponse response) {
        }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
        public void deleteEvent(@PathVariable("id") int id, HttpServletResponse response) {
        }

}
