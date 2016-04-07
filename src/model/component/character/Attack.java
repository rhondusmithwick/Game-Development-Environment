package model.component.character;

import model.component.DataComponent;


/**
 * The class for Attack. Contains a single double property.
 * {@inheritDoc}
 *
 * @author Roxanne Baker
 */

public class Attack extends DataComponent<Double> {

    /**
     * Empty constructor. Has attack at 0.
     */
    public Attack() {
    	super("Attack", 0.0);
    }

    /**
     * Construct with an initial value.
     * @param attack the initial value
     */
    public Attack(Double attack) {
       	this();
       	set(attack);
    }

    /**
     * @return String representation of attack
     */
    @Override
    public String toString() {
        return String.format("Attack: %s", get());
    }
}
