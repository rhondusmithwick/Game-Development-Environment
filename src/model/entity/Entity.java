package model.entity;

import customobjects.SerializableObservableMap;
import model.component.base.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Entity implements Serializable {
    private final Integer ID;

    private final SerializableObservableMap<Class<? extends Component>, List<Component>> observableMap = new SerializableObservableMap<>();

    public Entity(int ID) {
        this.ID = ID;
    }

    public void addComponent(Component component) {
        Class<? extends Component> theClass = component.getClassForComponentMap();
        if (!observableMap.containsKey(theClass)) {
            observableMap.put(theClass, new ArrayList<>());
        }
        if (component.unique()) {
            observableMap.get(theClass).clear();
        }
        observableMap.get(theClass).add(component);
    }

    public boolean hasComponent(Class<? extends Component> componentClass) {
        return observableMap.containsKey(componentClass);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass, int... index) {
        if (!hasComponent(componentClass)) {
            return null;
        }
        List<Component> componentStorage = observableMap.get(componentClass);
        T queriedComponent;
        if (index.length == 0) {
            queriedComponent = (T) componentStorage.get(0);
        } else {
            queriedComponent = (T) componentStorage.get(index[0]);
        }
        return queriedComponent;
    }

    public int getID() {
        return ID;
    }

    public void addComponentList(List<Component> components) {
        components.stream().forEach(this::addComponent);
    }

    public void addComponent(Component... components) {
        addComponentList(Arrays.asList(components));
    }


    @Override
    public String toString() {
        return String.format("ID: %d, Components: %s", ID, observableMap.toString());
    }
}
