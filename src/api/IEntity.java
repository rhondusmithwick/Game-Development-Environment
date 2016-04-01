package api;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


public interface IEntity extends Serializable {
    File serialize ();

    void update ();

    Collection<IComponent> getAllComponents ();

    <T extends IComponent> Collection<T> getComponents (Class<T> c);

    boolean addComponent (IComponent component);

    List<Boolean> addComponents (IComponent ... components);

    List<Boolean> addComponents (List<IComponent> components);

    boolean removeComponent (Class<IComponent> c);

    void setComponentQuantityRestriction (Class<IComponent> c, int n);

    int getComponentQuantityRestriction (Class<IComponent> c);
}
