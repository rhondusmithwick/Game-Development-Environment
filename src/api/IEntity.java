package api;


import com.google.common.base.Preconditions;
import model.entity.PropertiesTemplateLoader;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Interface for an entity.
 *
 * @author Rhondu Smithwick, Tom Wu
 */
public interface IEntity extends ISerializable {

    /**
     * Get this Entity's name.
     *
     * @return this entity's name
     */
    String getName();

    /**
     * Set this entity's name.
     *
     * @param name this entity's new name
     */
    void setName(String name);

    /**
     * Gets the unique id of this entity
     *
     * @return id the unique id of this entity
     */
    String getID();

    /**
     * Get all the components of this entity.
     *
     * @return all the components of this entity
     */
    Collection<IComponent> getAllComponents();

    /**
     * Get a list of components of this component type.
     *
     * @param componentClass the class of component
     * @param <T>            type of component
     * @return list of components of this component type
     */
    <T extends IComponent> List<T> getComponentList(Class<T> componentClass);

    /**
     * Get a single component of this type.
     *
     * @param componentClass the class of component
     * @param <T>            the type of component
     * @return a single component of this type
     */
    default <T extends IComponent> T getComponent(Class<T> componentClass) {
        return getComponent(componentClass, 0);
    }

    /**
     * Get a component of this type that was the index inserted.
     *
     * @param componentClass the class of component
     * @param index          the component number that it was inserted
     * @param <T>            the type of component
     * @return component of this type that was the index inserted
     * @throws IllegalArgumentException if no such index
     * @see #getComponentList(Class)
     */
    default <T extends IComponent> T getComponent(Class<T> componentClass, int index) throws IllegalArgumentException {
        List<T> componentStorage = getComponentList(componentClass);
        boolean validIndex = index < componentStorage.size();
        Preconditions.checkArgument(validIndex, "No such index");
        return componentStorage.get(index);
    }

    /**
     * Check if this entity has this component.
     *
     * @param componentClass to check
     * @param <T>            type of component
     * @return true if this entity has this component
     */
    <T extends IComponent> boolean hasComponent(Class<T> componentClass);

    /**
     * Check if this entity has this list of components.
     *
     * @param componentClasses list of components to check
     * @return true if entity has all these components
     * @see #hasComponent(Class)
     */
    default boolean hasComponents(List<Class<? extends IComponent>> componentClasses) {
        Predicate<Class<? extends IComponent>> doesNotHave = (c) -> (!hasComponent(c));
        return componentClasses.stream().noneMatch(doesNotHave);
    }

    /**
     * Check if this entity has this array/varargs of components.
     *
     * @param componentClasses list of components to check
     * @return true if entity has all these components
     * @see #hasComponents(List)
     */
    @SuppressWarnings("unchecked")
    default boolean hasComponents(Class<? extends IComponent>... componentClasses) {
        return hasComponents(Arrays.asList(componentClasses));
    }

    /**
     * Force add a component regardless of specs (enable true forceAdd).
     *
     * @param componentToAdd the component to add
     * @param forceAdd       whether to force add this component
     * @return true if this component was added
     */
    boolean forceAddComponent(IComponent componentToAdd, boolean forceAdd);


    /**
     * Force add list of components regardless of specs (enable true forceAdd).
     *
     * @param componentsToAdd the components to add
     * @param forceAdd        whether to force add these components
     * @return list of true values that indicates if a component was added
     * @see #forceAddComponent(IComponent, boolean)
     */
    default List<Boolean> forceAddComponents(List<IComponent> componentsToAdd, boolean forceAdd) {
        return componentsToAdd.stream().map(c -> forceAddComponent(c, forceAdd)).collect(Collectors.toList());
    }

    /**
     * Force add array/vargs of components regardless of specs (enable true forceAdd).
     *
     * @param componentsToAdd the components to add
     * @param forceAdd        whether to force add these components
     * @return list of true values that indicates if a component was added
     * @see #forceAddComponents(List, boolean)
     */
    default List<Boolean> forceAddComponents(boolean forceAdd, IComponent... componentsToAdd) {
        return forceAddComponents(Arrays.asList(componentsToAdd), forceAdd);
    }

    /**
     * Attempt to add a component (non force).
     *
     * @param componentToAdd the component to add
     * @return true if component added
     * @see #forceAddComponent(IComponent, boolean)
     */
    default boolean addComponent(IComponent componentToAdd) {
        return forceAddComponent(componentToAdd, false);
    }

    /**
     * Attempt to add a list of components (non force).
     *
     * @param componentsToAdd list of components to add
     * @return list of true values that indicates if a component was added
     * @see #forceAddComponents(List, boolean)
     */
    default List<Boolean> addComponents(List<IComponent> componentsToAdd) {
        return forceAddComponents(componentsToAdd, false);
    }

    /**
     * Attempt to add a array/varargs of components (non force).
     *
     * @param componentsToAdd array/varargs of components to add
     * @return list of true values that indicates if a component was added
     * @see #addComponents(List)
     */
    default List<Boolean> addComponents(IComponent... componentsToAdd) {
        return addComponents(Arrays.asList(componentsToAdd));
    }


    /**
     * Remove a component.
     *
     * @param componentClassToRemove component class to remove
     * @param <T>                    type of component
     * @return true if component was remove
     */
    Boolean removeComponent(Class<? extends IComponent> componentClassToRemove);

    /**
     * Remove list of components
     *
     * @param componentClassesToRemove list of component classes to remove
     * @return list of true values that indicates if a component was removed
     * @see #removeComponent(Class)
     */
    default List<Boolean> removeComponents(List<Class<? extends IComponent>> componentClassesToRemove) {
        return componentClassesToRemove.stream().map(this::removeComponent).collect(Collectors.toList());
    }

    /**
     * Remove array/varargs of components
     *
     * @param componentClassesToRemove array/varargs of component classes to remove
     * @return list of true values that indicates if a component was removed
     * @see #removeComponents(List)
     */
    @SuppressWarnings("unchecked")
    default List<Boolean> removeComponents(Class<? extends IComponent>... componentClassesToRemove) {
        return removeComponents(Arrays.asList(componentClassesToRemove));
    }

    /**
     * Get the specs, which is a map of component classes to how many this entity should have of each.
     *
     * @return the specs map
     */
    Map<Class<? extends IComponent>, Integer> getSpecs();

    /**
     * Load the specs from a resource properties file.
     *
     * @param fileName the fileName
     */
    default void loadSpecsFromPropertiesFile(String fileName) {
        ITemplateLoader<Class<? extends IComponent>> specLoader = new PropertiesTemplateLoader();
        Map<Class<? extends IComponent>, Integer> specs = specLoader.loadSpecs(fileName);
        setSpecs(specs);
    }

    /**
     * Set specs with a map.
     *
     * @param map to set specs with
     * @see #getSpecs()
     */
    default void setSpecs(Map<Class<? extends IComponent>, Integer> map) {
        getSpecs().clear();
        getSpecs().putAll(map);
    }

    /**
     * Get spec of a componentClass (how many components for this class).
     *
     * @param componentClass componentClass whose spec to get
     * @return spec of this component
     * @see #getSpecs()
     */
    default int getSpec(Class<? extends IComponent> componentClass) {
        return getSpecs().get(componentClass);
    }


    /**
     * Add a spec.
     *
     * @param componentClass class of the component
     * @param numToHave      how many this entity should have of this component
     * @param <T>            type of component
     * @see #getSpecs()
     */
    default <T extends IComponent> void setSpec(Class<T> componentClass, int numToHave) {
        getSpecs().put(componentClass, numToHave);
    }
}
