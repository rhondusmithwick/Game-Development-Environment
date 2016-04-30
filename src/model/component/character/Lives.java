package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

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

    /**
     * Decrease number of lives by 1.
     */
    public void decrementLives() {
        changeHelp(-1);
    }

    /**
     * Increase number of lives by 1.
     */
    public void incrementLives() {
        changeHelp(1);
    }

    private void changeHelp(int num) {
        setLives(getLives() + num);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }

    @Override
    public void update() {
        setLives(getLives());
    }
}
