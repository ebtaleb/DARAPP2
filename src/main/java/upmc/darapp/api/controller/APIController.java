package upmc.darapp.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

import upmc.darapp.api.dao.EventDAO;
import upmc.darapp.api.model.Event;

@RestController
@RequestMapping("/events")
public class APIController {

    @Autowired
    private EventDAO eventDAO;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
        public List<Event> getAllEvents() {
            return eventDAO.getAll();
        }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
        public Event fetchBy(@PathVariable("id") int id) {
            return eventDAO.get(id);
        }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
        public void createEvent(@RequestBody Event event, HttpServletResponse response) throws IOException {
            eventDAO.add(event);
            String result = "Event saved : " + event;
            PrintWriter out = response.getWriter();
            out.println(result);
        }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
        public void updateEvent(@RequestBody Event event, HttpServletResponse response) {
            eventDAO.update(event);
        }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
        public void deleteEvent(@PathVariable("id") int id, HttpServletResponse response) {
            eventDAO.delete(id);
        }
}
