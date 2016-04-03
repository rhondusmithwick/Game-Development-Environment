package model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import api.IComponent;
import customobjects.SerializableObservableMap;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Entity implements Serializable {
    private final Integer ID;

    private final SerializableObservableMap<Class<? extends IComponent>, List<IComponent>> observableMap =
            new SerializableObservableMap<>();

    public Entity (int ID) {
        this.ID = ID;
    }

    public void addComponent (IComponent component) {
        Class<? extends IComponent> theClass = component.getClassForComponentMap();
        if (!observableMap.containsKey(theClass)) {
            observableMap.put(theClass, new ArrayList<>());
        }
        if (component.unique()) {
            observableMap.get(theClass).clear();
        }
        observableMap.get(theClass).add(component);
    }

    public boolean hasComponent (Class<? extends IComponent> componentClass) {
        return observableMap.containsKey(componentClass);
    }

    public <T extends IComponent> List<T> getComponentList (Class<T> componentClass) {
        if (!hasComponent(componentClass)) {
            return null;
        }
        return observableMap.get(componentClass).stream()
                .map(componentClass::cast).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T extends IComponent> T getComponent (Class<T> componentClass, int ... index) {
        List<T> componentStorage = getComponentList(componentClass);
        if (index.length == 0) {
            return componentStorage.get(0);
        }
        else {
            return componentStorage.get(index[0]);
        }
    }

    public int getID () {
        return ID;
    }

    public void addComponentList (List<IComponent> components) {
        components.stream().forEach(this::addComponent);
    }

    public void addComponent (IComponent ... components) {
        addComponentList(Arrays.asList(components));
    }

    @Override
    public String toString () {
        return String.format("ID: %d, Components: %s", ID, observableMap.toString());
    }
}
