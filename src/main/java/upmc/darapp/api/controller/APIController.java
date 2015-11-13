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
import java.util.ArrayList;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import upmc.darapp.api.dao.EventDAO;
import upmc.darapp.api.model.Event;
import upmc.darapp.api.dao.CommentDAO;
import upmc.darapp.api.model.Comment;
import upmc.darapp.users.dao.UserDAO;
import upmc.darapp.users.model.User;

import upmc.darapp.api.dao.FollowDAO;
import upmc.darapp.api.model.Follow;

@RestController
@RequestMapping("/events")
public class APIController {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private FollowDAO followDAO;

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

    @RequestMapping(value = "/get/{id}/comments/get", method = RequestMethod.GET)
        public List<Comment> fetchCommentsByEventId(@PathVariable("id") int id) {
            return commentDAO.getAllCommentsForEvent(id);
        }

    @RequestMapping(value = "/get/{id}/comments/post", method = RequestMethod.POST)
        public @ResponseBody String addCommentToEvent(@PathVariable("id") int id, @RequestBody Comment c) {
            commentDAO.add(c);
            JSONObject json = new JSONObject();
            json.put("comment_creation", "success");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            json.put("creation_date", df.format(new Date()));
            return json.toString();
        }

    @RequestMapping(value = "/{user}/is_following", method = RequestMethod.GET)
        public @ResponseBody List<Event> eventsUserSubscribedTo(@PathVariable("user") String un) {
            return eventDAO.findUserEventSubscriptions(un);
        }

    @RequestMapping(value = "/{id}/{user}/follow", method = RequestMethod.POST)
        public void subscribeUserToEvent(@PathVariable("id") int id, @PathVariable("user") String u) {
            followDAO.addUserFollow(u, id);
        }

    @RequestMapping(value = "/{id}/{user}/follow", method = RequestMethod.DELETE)
        public void unsubscribeUserToEvent(@PathVariable("id") int id, @PathVariable("user") String u) {
            followDAO.removeUserFollow(u, id);
        }

    @RequestMapping(value = "/user_owned_events/{username}/get", method = RequestMethod.GET)
        public List<Event> getUserOwnedEvents(@PathVariable("username") String un) {
            List<Event> found = new ArrayList<Event>();
            if (userDAO.findByUserName(un) != null) {
                found = eventDAO.findUserOwnedEvents(un);
            } else {

            }
            return found;
        }

}
