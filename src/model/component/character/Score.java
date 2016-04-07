package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

/***
 * Holds the score.
 *
 * @author ajonnav, Rhondu Smithwick
 */
public class Score implements IComponent {

    /**
     * The singleProperty.
     */
    private final SingleProperty<Double> singleProperty;

    /**
     * Empty constructor. Starts at 0.0.
     */
    public Score() {
        singleProperty = new SingleProperty<>("Score", 0.0);
    }

    /**
     * Construct with an initial value.
     *
     * @param score the initial value
     */
    public Score(double score) {
        this();
        setScore(score);
    }

    /**
     * Get the score property.
     *
     * @return the score property
     */
    public SimpleObjectProperty<Double> scoreProperty() {
        return singleProperty.property1();
    }

    public double getScore() {
        return scoreProperty().get();
    }

    public void setScore(double score) {
        scoreProperty().set(score);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }
}