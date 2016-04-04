package api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public interface IEntity extends ISerializable {
    int getID();

    Collection<IComponent> getAllComponents();

    <T extends IComponent> List<T> getComponentList(Class<T> componentClass);

    default <T extends IComponent> T getComponent(Class<T> componentClass, int... index) {
        List<T> componentStorage = getComponentList(componentClass);
        if (index.length == 0) {
            return componentStorage.get(0);
        } else {
            return componentStorage.get(index[0]);
        }
    }

    boolean hasComponent(Class<? extends IComponent> c);

    default boolean hasComponents(List<Class<? extends IComponent>> componentsClasses) {
        Predicate<Class<? extends IComponent>> doesNotHave = (c) -> (!hasComponent(c));
        return componentsClasses.stream().noneMatch(doesNotHave);
    }

    default boolean hasComponents(Class<? extends IComponent>... componentClasses) {
        return hasComponents(Arrays.asList(componentClasses));
    }

    boolean addComponent(IComponent component);

    default List<Boolean> addComponents(List<IComponent> components) {
        return components.stream().map(this::addComponent).collect(Collectors.toList());
    }

    default List<Boolean> addComponents(IComponent... components) {
        return addComponents(Arrays.asList(components));
    }

    boolean removeComponent(Class<? extends IComponent> componentClass);

    Map<Class<? extends IComponent>, Integer> getSpecs();

    default void setSpecs(Map<Class<? extends IComponent>, Integer> map) {
        getSpecs().clear();
        getSpecs().putAll(map);
    }

    default int getSpec(Class<? extends IComponent> componentClass) {
        return getSpecs().get(componentClass);
    }

    default void setSpec(Class<? extends IComponent> compnentClass, int n) {
        getSpecs().put(compnentClass, n);
    }
}
