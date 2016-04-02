package api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface IEntity extends Serializable {
    Collection<IComponent> getAllComponents ();

    <T extends IComponent> Collection<T> getComponents (Class<T> c);

    boolean addComponent (IComponent component);

    List<Boolean> addComponents (IComponent ... components);

    List<Boolean> addComponents (List<IComponent> components);

    boolean removeComponent (Class<IComponent> c);

    Map<Class<IComponent>, Integer> getSpecs ();

    void setSpecs (Map<Class<IComponent>, Integer> map);

    int getSpec (Class<IComponent> c);

    void setSpec (Class<IComponent> c, int n);
}
