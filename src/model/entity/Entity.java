package model.entity;

import model.component.base.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Entity implements Serializable {
    private final Integer ID;
    private final Map<Class<? extends Component>, List<Component>> components = new HashMap<>();

    public Entity(int ID) {
        this.ID = ID;
    }

    public void addComponent(Component component) {
        Class<? extends Component> theClass = component.getClassForComponentMap();
        if (!components.containsKey(theClass)) {
            components.put(theClass, new ArrayList<>());
        }
        if (component.unique()) {
            components.get(theClass).clear();
        }
        components.get(theClass).add(component);

    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> componentClass, int... index) {
        List<Component> componentStorage = components.get(componentClass);
        T queriedComponent;
        if (index.length == 0) {
            queriedComponent = (T) componentStorage.get(0);
        } else {
            queriedComponent = (T) componentStorage.get(index[0]);
        }
        if (queriedComponent == null) {
//            throw new NoComponentFoundException();
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
