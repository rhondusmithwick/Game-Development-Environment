package api;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface IEntity extends ISerializable {
    Collection<IComponent> getAllComponents();

    <T extends IComponent> T getComponent(Class<T> c);

    <T extends IComponent> List<T> getComponentList(Class<T> componentClass);

    boolean hasComponent(Class<? extends IComponent> c);

    boolean hasComponents(Class<? extends IComponent>... c);

    boolean addComponent(IComponent component);

    List<Boolean> addComponents(IComponent... components);

    List<Boolean> addComponents(List<IComponent> components);

    boolean removeComponent(Class<? extends IComponent> c);

    Map<Class<? extends IComponent>, Integer> getSpecs();

    void setSpecs(Map<Class<? extends IComponent>, Integer> map);

    int getSpec(Class<? extends IComponent> c);

    void setSpec(Class<? extends IComponent> c, int n);
}
