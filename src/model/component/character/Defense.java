package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;


/**
 * The Defense property. Holds a single double property.
 *
 * @author Roxanne Baker, Rhondu Smithwick
 */
@SuppressWarnings("serial")
public class Defense implements IComponent {

    /**
     * The singleProperty.
     */
    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Defense", 0.0);

    /**
     * Construct to start defense at 0.
     */
    public Defense () {
    }

    /**
     * Construct with initial value.
     *
     * @param defense the initial defense value
     */
    public Defense (double defense) {
        setDefense(defense);
    }

    /**
     * Get the defense property.
     *
     * @return the defense property
     */
    public SimpleObjectProperty<Double> defenseProperty () {
        return singleProperty.property1();
    }

    public double getDefense () {
        return defenseProperty().get();
    }

    public void setDefense (double defense) {
        defenseProperty().set(defense);
    }

    @Override
    public String toString () {
        return String.format("Defense: %s", getDefense());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return singleProperty.getProperties();
    }

    @Override
    public void update () {
        setDefense(getDefense());
    }
}