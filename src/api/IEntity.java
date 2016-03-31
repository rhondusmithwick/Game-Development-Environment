package api;

import java.io.File;
import java.util.Collection;
import java.util.List;


public interface IEntity {
    File serialize ();

    Collection<IComponent> getAllComponents ();

    <T extends IComponent> Collection<T> getComponents (Class<T> c);

    boolean addComponent (IComponent component);

    List<Boolean> addComponents (IComponent ... components);

    List<Boolean> addComponents (List<IComponent> components);

    boolean removeComponent (Class<IComponent> c);

    void setComponentQuantityRestriction (Class<? extends IComponent> c, int n);
}
