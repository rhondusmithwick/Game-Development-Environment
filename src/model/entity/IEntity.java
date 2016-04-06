package model.entity;

import api.ISerializable;
import model.component.IComponent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public interface IEntity extends ISerializable {
	/**
	 * Gets the unique id of this entity
	 * @return id the unique id of this entity
	 */
    int getID();

    @Deprecated
    Collection<IComponent> getAllComponents();

    /**
	 * Gets a List of all IComponents of componentClass that this entity possesses
	 * @param componentClass the Class of IComponents in List<T>
	 * @return List of T-type IComponents of Class componentClass
	 */
    <T extends IComponent> List<T> getComponentList(Class<T> componentClass);

    /**
	 * Gets the first component of componentClass that this entity possesses
	 * @param componentClass the Class of the IComponent T
	 * @return T-type IComponent of Class componentClass
	 */
    default <T extends IComponent> T getComponent(Class<T> componentClass) {
        return getComponent(componentClass, 0);
    }

    /**
	 * Gets the i'th component of componentClass that this entity possesses
	 * @param componentClass the Class of the IComponent T
	 * @param index i of the component
	 * @return T-type IComponent of Class componentClass
	 */
    default <T extends IComponent> T getComponent(Class<T> componentClass, int index) {
        List<T> componentStorage = getComponentList(componentClass);
        return componentStorage.get(index);
    }

    /**
	 * Checks whether this entity possesses any component(s) of componentClass
	 * @param componentClass the Class of the IComponent T
	 * @return whether this entity possesses any component(s) of componentClass
	 */
    <T extends IComponent> boolean hasComponent(Class<T> componentClass);

    /**
	 * Checks whether this entity possesses all component(s) in componentClasses
	 * @param componentClasses the List of Classes of IComponent types to be checked
	 * @return whether this entity possesses all component(s) in componentClasses
	 */
    default boolean hasComponents(List<Class<? extends IComponent>> componentsClasses) {
        Predicate<Class<? extends IComponent>> doesNotHave = (c) -> (!hasComponent(c));
        return componentsClasses.stream().noneMatch(doesNotHave);
    }

    /**
	 * Checks whether this entity possesses all component(s) in componentClasses
	 * @param componentClasses the Array of Classes of IComponent types to be checked
	 * @return whether this entity possesses all component(s) in componentClasses
	 */
    default boolean hasComponents(Class<? extends IComponent>... componentClasses) {
        return hasComponents(Arrays.asList(componentClasses));
    }

    boolean forceAddComponent(IComponent componentToAdd, boolean forceAdd);


    default List<Boolean> forceAddComponents(List<IComponent> components, boolean forceAdd) {
        return components.stream().map(c -> forceAddComponent(c, forceAdd)).collect(Collectors.toList());
    }

    default List<Boolean> forceAddComponents(boolean forceAdd, IComponent... components) {
        return forceAddComponents(Arrays.asList(components), forceAdd);
    }

    default boolean addComponent(IComponent componentToAdd) {
        return forceAddComponent(componentToAdd, false);
    }

    default List<Boolean> addComponents(List<IComponent> componentsToAdd) {
        return forceAddComponents(componentsToAdd, false);
    }

    default List<Boolean> addComponents(IComponent... componentsToAdd) {
        return addComponents(Arrays.asList(componentsToAdd));
    }

    <T extends IComponent> boolean removeComponent(Class<T> componentClassToRemove);

    default List<Boolean> removeComponents(List<Class<? extends IComponent>> componentClassesToRemove) {
        return componentClassesToRemove.stream().map(this::removeComponent).collect(Collectors.toList());
    }

    default List<Boolean> removeComponents(Class<? extends IComponent>... componentClassesToRemove) {
        return removeComponents(Arrays.asList(componentClassesToRemove));
    }

    Map<Class<? extends IComponent>, Integer> getSpecs();

    default void setSpecs(Map<Class<? extends IComponent>, Integer> map) {
        getSpecs().clear();
        getSpecs().putAll(map);
    }

    default int getSpec(Class<? extends IComponent> componentClass) {
        return getSpecs().get(componentClass);
    }

    default <T extends IComponent> void setSpec(Class<T> componentClass, int numToHave) {
        getSpecs().put(componentClass, numToHave);
    }
}
