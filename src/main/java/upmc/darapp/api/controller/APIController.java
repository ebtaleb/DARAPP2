package upmc.darapp.api.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;

import upmc.darapp.users.model.User;
import upmc.darapp.users.dao.UserDAO;

import upmc.darapp.api.model.Event;
import upmc.darapp.api.dao.EventDAO;

import upmc.darapp.api.model.Comment;
import upmc.darapp.api.dao.CommentDAO;

import upmc.darapp.api.model.Follow;
import upmc.darapp.api.dao.FollowDAO;

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
        public List<Event> getAllEvents() {
            return eventDAO.getAll();
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

    @RequestMapping(value = "/{id}/get_comments", method = RequestMethod.GET)
        public List<Comment> fetchCommentsByEventId(@PathVariable("id") int id) {
            return commentDAO.getAllCommentsForEvent(id);
        }

    @RequestMapping(value = "/get/{id}/post_comment", method = RequestMethod.POST)
        public @ResponseBody String addCommentToEvent(@PathVariable("id") int id, @RequestBody Comment c) {
            commentDAO.add(c);
            JSONObject json = new JSONObject();
            json.put("comment_creation", "success");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            json.put("creation_date", df.format(new Date()));
            return json.toString();
        }

    @RequestMapping(value = "/{user}/get_subscriptions", method = RequestMethod.GET)
        public @ResponseBody List<Event> eventsUserSubscribedTo(@PathVariable("user") String un) {
            return eventDAO.findUserEventSubscriptions(un);
        }

    @RequestMapping(value = "/{id}/{user}/subscribe", method = RequestMethod.POST)
        public void userSubscribeToEvent(@PathVariable("id") int id, @PathVariable("user") String u) {
            followDAO.addUserFollow(u, id);
        }

    @RequestMapping(value = "/{id}/{user}/unsubscribe", method = RequestMethod.DELETE)
        public void userUnsubscribeToEvent(@PathVariable("id") int id, @PathVariable("user") String u) {
            followDAO.removeUserFollow(u, id);
        }

    @RequestMapping(value = "/{user}/get_user_owned_events", method = RequestMethod.GET)
        public List<Event> getUserOwnedEvents(@PathVariable("user") String un) {
            List<Event> found = new ArrayList<Event>();
            if (userDAO.findByUserName(un) != null) {
                found = eventDAO.findUserOwnedEvents(un);
            } else {

            }
            return found;
        }
}
