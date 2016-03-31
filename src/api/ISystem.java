package api;

import java.io.File;
import java.util.Collection;


public interface ISystem {
    File serialize();

    void evaluate(File xml);

    // Entities
    Collection<IEntity> getAllEntities();

    void addEntity(IEntity entity);

    void addEntities(Collection<IEntity> entities);

    Collection<?> getEntities(Class<? extends IEntity> c);

    boolean removeEntity(Class<? extends IEntity> c);

    // Events
    Collection<IEvent> getAllEvents();

    void addEvent(IEvent event);

    void addEvents(Collection<IEvent> events);

    Collection<?> getEvents(Class<? extends IEvent> c);

    boolean removeEvent(Class<? extends IEvent> c);
}
