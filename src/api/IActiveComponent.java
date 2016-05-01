package api;

@Deprecated
/**
 * The interface for an active component.
 *
 * @author Tom Wu
 */
public interface IActiveComponent extends IComponent {
    /**
     * Can act on anything in the system
     *
     * @param system might need to be restricted later!
     */
    void act (ISystem system);
}
