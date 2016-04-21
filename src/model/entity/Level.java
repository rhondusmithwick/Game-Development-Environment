package model.entity;

import api.IComponent;
import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import api.IPhysicsEngine;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import events.EventSystem;
import model.physics.PhysicsEngine;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Implementation of an entity system. This implementation is focused on the
 * IDs. It spawns entities based on the next available ID and adds them to the
 * system.
 *
 * @author Rhondu Smithwick
 */
public class Level implements ILevel {

    /**
     * The entities in this system.
     */
    @XStreamAlias("entities")
    private final Map<String, IEntity> entities = Maps.newLinkedHashMap();
    private String name;
    private Map<String, String> metadata = Maps.newLinkedHashMap();
    private IEventSystem eventSystem = new EventSystem(this);
    // update
    private IPhysicsEngine physics = new PhysicsEngine();
    private String eventSystemPath;

    public Level() {
        this("");
    }

    public Level(String name) {
        this.name = name;
    }

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

    @Override
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    @Override
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public void addMetadata(String key, String value) {
        this.metadata.put(key, value);
    }

    @Override
    public void init(List<File> groovyScripts) {
        // TODO: class-load all Groovy scripts (pass in physics too) and call
        // their init() methods
    }

    @Override
    public void update(double dt) {
        // TODO: call the scripts' update() methods
    }

    @Override
    public IEventSystem getEventSystem() {
        return this.eventSystem;
    }

    @Override
    public IPhysicsEngine getPhysicsEngine() {
        return this.physics;
    }

    public String getEventSystemPath() {
        return eventSystemPath;
    }

    @Override
    public void setEventSystemPath(String eventSystemPath) {
        this.eventSystemPath = eventSystemPath;
    }

}
