
package model.entity;

import api.IComponent;
import api.IEntity;
import api.IEntitySystem;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

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
    public IEntity createEntity() {
        IEntity entity = new Entity();
        addEntity(entity);
        return entity;
    }

    @Override
    public IEntity addEntity(IEntity entity) {
        if(containsID(entity.getID())) {
            return null;
        }
        entities.add(entity);
        return entity;
    }

    @Override
    public IEntity getEntity(String i) {
        for(IEntity e:entities) {
            if(e.getID().equals(i)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<IEntity> getAllEntities() {
        return entities;
    }

    @Override
    public boolean containsID(String id) {
        for(IEntity e:entities) {
            if(e.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IEntity removeEntity(String id) {
        if (containsID(id)) {
            for(IEntity e:entities) {
                if(e.getID().equals(id)) {
                    e.getAllComponents().stream().forEach(IComponent::removeBindings);
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isEmpty() {
        return this.getAllEntities().isEmpty();
    }

}
