package model.component.character;

import model.component.DataComponent;


/**
 * The class for Score. Contains a single double property.
 * {@inheritDoc}
 *
 * @author Roxanne Baker
 */
public class Score extends DataComponent<Double> {

    /**
     * Empty constructor. Has attack at 0.
     */
    public Score() {
    	super("Score", 0.0);
    }

    /**
     * Construct with an initial value.
     * @param attack the initial value
     */
    public Score(Double score) {
       	this();
       	set(score);
    }

    /**
     * @return String representation of score
     */
    @Override
    public String toString() {
        return String.format("Score: %s", get());
    }
}
