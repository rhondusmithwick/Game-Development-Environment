package api;

import java.io.File;
import java.util.Collection;


public interface IEntity {
    File getXML ();

    void addComponent (IComponent component);

    void addComponents (Collection<IComponent> components);

    void getComponents (Class<? extends IComponent> c);

    void removeComponent (Class<? extends IComponent> c);
}
