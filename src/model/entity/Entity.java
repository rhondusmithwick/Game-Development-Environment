package model.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import model.component.IComponent;

import java.util.Collection;
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
    private final ObservableMap<Class<? extends IComponent>, List<IComponent>> componentMap =
            FXCollections.observableHashMap();


    @XStreamAlias("Specs")
    private final Map<Class<? extends IComponent>, Integer> specs = Maps.newLinkedHashMap();

    public Entity(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public Collection<IComponent> getAllComponents() {
        return componentMap.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toList());
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
    public boolean hasComponent(Class<? extends IComponent> componentClass) {
        return componentMap.containsKey(componentClass);
    }

    @Override
    public boolean addComponent(IComponent component) {
        Class<? extends IComponent> theClass = component.getClassForComponentMap();
        if (!componentMap.containsKey(theClass)) {
            componentMap.put(theClass, Lists.newArrayList());
        }
        if (component.unique()) {
            componentMap.get(theClass).clear();
        }
        return componentMap.get(theClass).add(component);
    }

    @Override
    public boolean removeComponent(Class<? extends IComponent> componentClass) {
        if (componentMap.containsKey(componentClass)) {
            componentMap.remove(componentClass);
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
        return String.format("ID: %d, Components: %s", ID, componentMap.toString());
    }
}
