package api;

import java.io.File;
import java.util.Collection;
import java.util.List;


public interface ISystem {
    File evaluate (File f);

    void update ();

    File serialize ();

    // Entities
    Collection<IEntity> getAllEntities ();

    boolean addEntity (IEntity entity);

    List<Boolean> addEntities (IEntity ... entities);

    List<Boolean> addEntities (List<IEntity> entities);

    <T extends IEntity> Collection<T> getEntities (Class<T> c);

    boolean removeEntity (Class<IEntity> c);

    // Events
    Collection<IEvent> getAllEvents ();

    boolean addEvent (IEvent event);

    List<Boolean> addEvents (IEvent ... events);

    List<Boolean> addEvents (List<IEvent> events);

    <T extends IEvent> Collection<T> getEvents (Class<T> c);

    boolean removeEvent (Class<IEvent> c);
}
