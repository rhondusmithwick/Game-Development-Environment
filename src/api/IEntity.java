package api;

import java.io.File;
import java.util.Collection;


public interface IEntity {
    File serialize();

    void evaluate(File xml);

    Collection<IComponent> getAllComponents();

    void addComponent(IComponent component);

    void addComponents(Collection<IComponent> components);

    Collection<?> getComponents(Class<? extends IComponent> c);

    boolean removeComponent(Class<? extends IComponent> c);
}
