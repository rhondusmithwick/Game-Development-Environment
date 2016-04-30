
package model.entity;

import api.IComponent;
import api.IEntity;
import api.IEntitySystem;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Tom on 4/24/2016.
 */
public class EntitySystem implements IEntitySystem {

    /**
     * The entities in this system.
     */
    @XStreamAlias("entities")
    private final List<IEntity> entities = Lists.newArrayList();
    private String name;

    @Override
    public IEntity createEntity () {
        IEntity entity = new Entity();
        addEntity(entity);
        return entity;
    }

    @Override
    public IEntity addEntity (IEntity entity) {
        if (containsID(entity.getID())) {
            return null;
        }
        entities.add(entity);
        return entity;
    }

    @Override
    public IEntity getEntity (String id) {
        return entitiesWIthID(id).findFirst().orElse(null);
    }

    @Override
    public List<IEntity> getAllEntities () {
        return entities;
    }

    @Override
    public boolean containsID (String id) {
        return entitiesWIthID(id).count() > 0;
    }

    @Override
    public IEntity removeEntity (String id) {
        if (containsID(id)) {
            Iterator<IEntity> iter = entities.iterator();
            while (iter.hasNext()) {
                IEntity entity = iter.next();
                if (entity.getID().equals(id)) {
                    entity.getAllComponents().stream().forEach(IComponent::removeBindings);
                    iter.remove();
                    return entity;
                }
            }
        }
        return null;
    }

    @Override
    public String getName () {
        return this.name;
    }

    @Override
    public void setName (String name) {
        this.name = name;
    }

    @Override
    public boolean isEmpty () {
        return this.getAllEntities().isEmpty();
    }

    private Stream<IEntity> entitiesWIthID (String id) {
        Predicate<IEntity> equalID = (e) -> e.getID().equals(id);
        return entities.stream().filter(equalID);
    }
}
