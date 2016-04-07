package api;

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
     * Return the class to put into an entity map.
     *
     * @return the class to be put into an Entity map
     */
    default Class<? extends IComponent> getClassForComponentMap() {
        return getClass();
    }
}
