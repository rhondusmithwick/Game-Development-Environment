package model.component;

import api.ISystem;

/**
 * The interface for an active component.
 *
 * @author Tom Wu
 */
public interface IActiveComponent extends IComponent {
    /**
     * Act ont a system.
     *
     * @param system to act on
     */
    void act(ISystem system);
}
