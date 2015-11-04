package upmc.darapp.controller.api.controller;

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

import upmc.darapp.api.model.Track;

@RestController
@RequestMapping("/metallica")
public class TrackController {

    @Autowired
    SessionFactory sessionFactory;

    Session session = null;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
        public List<Track> getAllTracks() {

            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Track");

            List<Track> ts = query.list();

            session.getTransaction().commit();
            session.close();

            return ts;
        }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
        public Track fetchBy(@PathVariable("id") int id) {

            session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Track where ID=" + id);
            Track found = (Track) query.uniqueResult();;

            session.getTransaction().commit();
            session.close();

            return found;
        }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
        public void createTrack(@RequestBody Track track, HttpServletResponse response) throws IOException {

            session = sessionFactory.openSession();
            session.beginTransaction();

            String result = "Track saved : " + track;
            session.save(track);

            session.getTransaction().commit();
            session.close();
            PrintWriter out = response.getWriter();
            out.println(result);
        }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
        public void update(@RequestBody Track track, HttpServletResponse response) {
            // update notification
        }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
        public void deleteTrack(@PathVariable("id") int id, HttpServletResponse response) {
            // deleting notification
        }

}
