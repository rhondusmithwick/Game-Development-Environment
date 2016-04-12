package model.entity;

import api.IComponent;
import api.IEntity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of an IEntity.
 * <p>
 * This implementation uses Java's UUID to generate unique IDs.
 *
 * @author Rhondu Smithwick
 */
public class Entity implements IEntity {

    @XStreamAsAttribute()
    private final String ID;

    @XStreamAsAttribute()
    private String name;

    @XStreamAlias("components")
    private final ObservableMap<Class<? extends IComponent>, List<IComponent>> componentMap = FXCollections
            .observableHashMap();

    @XStreamAlias("Specs")
    private final Map<Class<? extends IComponent>, Integer> specs = Maps.newLinkedHashMap();

    public Entity() {
        this("John Doe");
    }

    public Entity(String name) {
        ID = UUID.randomUUID().toString();
        setName(name);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public Collection<IComponent> getAllComponents() {    	
        return componentMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public <T extends IComponent> List<T> getComponentList(Class<T> componentClass) {
        if (!hasComponent(componentClass)) {
            return null;
        }
        List<IComponent> currentComponents = componentMap.get(componentClass);
        return Lists.transform(currentComponents, componentClass::cast);
    }

    @Override
    public <T extends IComponent> boolean hasComponent(Class<T> componentClass) {
        return componentMap.containsKey(componentClass);
    }

    @Override
    public boolean forceAddComponent(IComponent componentToAdd, boolean forceAdd) {
        Class<? extends IComponent> componentClass = componentToAdd.getClassForComponentMap();
        boolean noTemplate = specs.isEmpty();
        boolean preCondition = forceAdd || noTemplate || specs.containsKey(componentClass);
        if (!preCondition) {
            return false;
        }
        if (!componentMap.containsKey(componentClass)) {
            componentMap.put(componentClass, Lists.newArrayList());
        }
        List<IComponent> componentStore = componentMap.get(componentClass);
        boolean eligibleByForceOrSize = forceAdd || noTemplate || (componentStore.size() < getSpec(componentClass));
        return eligibleByForceOrSize && componentStore.add(componentToAdd);
    }

    @Override
    public <T extends IComponent> Boolean removeComponent(Class<? extends IComponent> componentClassToRemove) {
        if (componentMap.containsKey(componentClassToRemove)) {
            componentMap.remove(componentClassToRemove);
            return true;
        }
        return false;
    }

    @Override
    public Map<Class<? extends IComponent>, Integer> getSpecs() {
        return specs;
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Components: %s", ID, componentMap.toString());
    }

}