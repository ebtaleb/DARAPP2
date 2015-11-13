package upmc.darapp.api.dao;

import java.util.List;

import upmc.darapp.api.model.Event;

public interface EventDAO {
    Event get(int id);
    void add(Event event);
    List<Event> getAll();
    List<Event> findUserOwnedEvents(String u);
    List<Event> findUserEventSubscriptions(String u);

}
