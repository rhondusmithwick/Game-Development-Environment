
package model.entity;

import api.IComponent;
import api.IEntity;
import api.IEntitySystem;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Tom on 4/24/2016.
 */
public class EntitySystem implements IEntitySystem {

    /**
     * The entities in this system.
     */
    @XStreamAlias("entities")
    private final Map<String, IEntity> entities = Maps.newLinkedHashMap();
    private String name;

    @Override
    public IEntity createEntity() {
        IEntity entity = new Entity();
        addEntity(entity);
        return entity;
    }

    @Override
    public IEntity addEntity(IEntity entity) {
        return entities.put(entity.getID(), entity);
    }

    @Override
    public IEntity getEntity(String i) {
        return entities.get(i);
    }

    @Override
    public Collection<IEntity> getAllEntities() {
        return entities.values();
    }

    @Override
    public boolean containsID(String id) {
        return entities.containsKey(id);
    }

    @Override
    public boolean removeEntity(String id) {
        if (containsID(id)) {
            IEntity entity = entities.remove(id);
            entity.getAllComponents().stream().forEach(IComponent::removeBindings);
            return true;
        }
        return false;
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
