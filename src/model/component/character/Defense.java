package model.component.character;

import model.component.DataComponent;


/**
 * The class for Defense. Contains a single double property.
 * {@inheritDoc}
 *
 * @author Roxanne Baker
 */

public class Defense extends DataComponent<Double> {

    /**
     * Empty constructor. Has attack at 0.
     */
    public Defense() {
    	super("Defense", 0.0);
    }

    /**
     * Construct with an initial value.
     * @param attack the initial value
     */
    public Defense(Double defense) {
       	this();
       	set(defense);
    }

    /**
     * @return String representation of defense
     */
    @Override
    public String toString() {
        return String.format("Defense: %s", get());
    }
}
