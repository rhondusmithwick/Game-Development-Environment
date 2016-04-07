package model.component.character;

import model.component.DataComponent;


/**
 * The class for Health. Contains a single double property.
 * {@inheritDoc}
 *
 * @author Roxanne Baker
 */

public class Health extends DataComponent<Double> {

    /**
     * Empty constructor. Has attack at 0.
     */
    public Health() {
    	super("Health", 0.0);
    }

    /**
     * Construct with an initial value.
     * @param attack the initial value
     */
    public Health(Double health) {
       	this();
       	set(health);
    }

    /**
     * @return String representation of health
     */
    @Override
    public String toString() {
        return String.format("Health: %s", get());
    }
}
