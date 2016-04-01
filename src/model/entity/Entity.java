package model.entity;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import model.component.base.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Entity implements Serializable {
    private final Integer ID;
    private final Map<Class<? extends Component>, List<Component>> components = new HashMap<>();

    private final transient ObservableMap<Class<? extends Component>, List<Component>> observableMap = FXCollections.observableMap(components);

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

    public void addListener(MapChangeListener<Class<? extends Component>, List<Component>> listener) {
        observableMap.addListener(listener);
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

    public void addComponent(Component... components) {
        addComponentList(Arrays.asList(components));
    }

    public void addComponentList(List<Component> components) {
        components.stream().forEach(this::addComponent);
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Components: %s", ID, components.toString());
    }
}
