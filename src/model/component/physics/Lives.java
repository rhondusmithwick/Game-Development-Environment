package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

/**
 * The class containing a property for lives.
 *
 * @author Rhondu Smithwick
 */
public class Lives implements IComponent {
    /**
     * The single property. Contains a single Lives Integer property.
     */
    private final SingleProperty<Integer> singleProperty = new SingleProperty<>("Lives", 0);

    /**
     * Empty constructor that default to 0.
     */
    public Lives() {
    }

    /**
     * Construct with an initial value.
     *
     * @param lives the initial value
     */
    public Lives(int lives) {
        setLives(lives);
    }

    /**
     * Get the lives property.
     *
     * @return the lives property
     */
    public SimpleObjectProperty<Integer> livesProperty() {
        return singleProperty.property1();
    }

    public int getLives() {
        return livesProperty().get();
    }

    public void setLives(int lives) {
        livesProperty().set(lives);
    }
}
