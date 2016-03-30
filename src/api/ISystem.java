package api;

import java.util.Collection;


public interface ISystem {
    Collection<IEntity> getEntities ();

    void addEntity (IEntity entity);

    void addEntities (Collection<IEntity> entities);

    void getEntities (Class<? extends IEntity> c);

    void removeEntity (Class<? extends IEntity> c);
}
