package api;

import com.google.common.base.Preconditions;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This is the interface for all components, which hold data.
 *
 * @author Rhondu Smithwick, Tom Wu
 */
public interface IComponent extends ISerializable {
    /**
     * Returns if only one of this component is allowed.
     *
     * @return whether only one of these components is allowed for an Entity
     */
    @Deprecated
    default boolean unique() {
        return false;
    }

    /**
     * Gets any properties this component holds.
     *
     * @return all the properties this coompoennt holds
     */
    default List<SimpleObjectProperty<?>> getProperties() {
        return Collections.emptyList();
    }

    /**
     * Gets a specific property of specified class nd with specified name.
     *
     * @param propertyClass the class of the property
     * @param name          the name of property
     * @param <T>           the type
     * @return specific property of specified class and with name
     */
    @SuppressWarnings("unchecked")
    default <T> T getProperty(Class<T> propertyClass, String name) {
        Predicate<SimpleObjectProperty> isRight = (s) -> (Objects.equals(s.getName(), name));
        Optional<SimpleObjectProperty<?>> rightProperty = getProperties().stream().filter(isRight).findFirst();
        boolean hasProperty = rightProperty.isPresent();
        Preconditions.checkArgument(hasProperty, "No such name present");
        boolean rightClass = propertyClass.isInstance(rightProperty);
        Preconditions.checkArgument(rightClass, "Incorrect class");
        return propertyClass.cast(rightProperty.get());
    }

    /**
     * Return the class to put into an entity map.
     *
     * @return the class to be put into an Entity map
     */
    default Class<? extends IComponent> getClassForComponentMap() {
        return getClass();
    }
}
