package api;

import java.util.Collection;
import java.util.List;


public interface IEntitySystem extends ISystem {
    Collection<IEntity> getAllEntities ();

    <T extends IEntity> Collection<T> getEntities (Class<T> c);

    boolean addEntity (IEntity entity);

    List<Boolean> addEntities (IEntity ... entities);

    List<Boolean> addEntities (List<IEntity> entities);

    boolean removeEntity (Class<IEntity> c);
}
