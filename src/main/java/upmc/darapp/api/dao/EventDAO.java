package upmc.darapp.api.dao;

import upmc.darapp.api.model.Event;
import java.util.List;

public interface EventDAO {
    List<Event> getAll();
    List<Event> findUserOwnedEvents(String u);
    Event get(int id);
    List<Event> findUserEventSubscriptions(String u);
    void add(Event event);
}
