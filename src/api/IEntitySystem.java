package api;

import java.util.Collection;
import java.util.List;


public interface IEntitySystem extends ISerializable {
    Collection<IEntity> getAllEntities ();

    IEntity getEntity (int id);

    boolean addEntity (IEntity entity);

    List<Boolean> addEntities (IEntity ... entities);

    List<Boolean> addEntities (List<IEntity> entities);

    boolean removeEntity (int id);
}
