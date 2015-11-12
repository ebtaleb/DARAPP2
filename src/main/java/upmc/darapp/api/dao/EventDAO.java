package upmc.darapp.api.dao;

import upmc.darapp.api.model.Event;
import java.util.List;

public interface EventDAO {
    List<Event> getAll();
    List<Event> findUserOwnedEvents(String u);
    Event get(int id);
    void add(Event event);
    void update(Event event);
    void delete(int id);
}