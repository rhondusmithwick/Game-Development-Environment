package model.entity;

import api.IComponent;
import api.IEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Entity implements IEntity {

    @XStreamAsAttribute()
    private final Integer ID;

    @XStreamAlias("components")
    private final ObservableMap<Class<? extends IComponent>, List<IComponent>> observableMap =
            FXCollections.observableHashMap();

    @XStreamAlias("Specs")
    private final Map<Class<? extends IComponent>, Integer> specs = new HashMap<>();

    public Entity(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Collection<IComponent> getAllComponents() {
        return observableMap.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public <T extends IComponent> List<T> getComponentList(Class<T> componentClass) {
        if (!hasComponent(componentClass)) {
            return null;
        }
        return observableMap.get(componentClass).stream()
                .map(componentClass::cast).collect(Collectors.toList());
    }


    @Override
    public boolean hasComponent(Class<? extends IComponent> componentClass) {
        return observableMap.containsKey(componentClass);
    }

    @Override
    public boolean addComponent(IComponent component) {
        Class<? extends IComponent> theClass = component.getClassForComponentMap();
        if (!observableMap.containsKey(theClass)) {
            observableMap.put(theClass, new ArrayList<>());
        }
        if (component.unique()) {
            observableMap.get(theClass).clear();
        }
        return observableMap.get(theClass).add(component);
    }

    @Override
    public boolean removeComponent(Class<? extends IComponent> componentClass) {
        if (observableMap.containsKey(componentClass)) {
            observableMap.remove(componentClass);
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
        return String.format("ID: %d, Components: %s", ID, observableMap.toString());
    }
}
