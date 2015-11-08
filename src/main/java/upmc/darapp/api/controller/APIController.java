package upmc.darapp.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
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
        public @ResponseBody String createEvent(@RequestBody Event event) {
            eventDAO.add(event);
            List<Event> l = eventDAO.getAll();
            Event last = l.get(l.size() - 1);
            JSONObject json = new JSONObject();
            json.put("created", "success");
            json.put("id", last.getId());
            return json.toString();
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
