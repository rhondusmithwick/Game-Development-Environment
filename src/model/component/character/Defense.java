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
public class Defense implements IComponent {

    /**
     * The singleProperty.
     */
    private final SingleProperty<Double> singleProperty;

    /**
     * Construct to start defense at 0.
     */
    public Defense() {
        singleProperty = new SingleProperty<>("Defense", 0.0);
    }

    /**
     * Construct with initial value.
     *
     * @param defense the initial defense value
     */
    public Defense(Double defense) {
        this();
        setDefense(defense);
    }

    /**
     * Get the defense property.
     *
     * @return the defense property
     */
    public SimpleObjectProperty<Double> defenseProperty() {
        return singleProperty.property1();
    }

    public double getDefense() {
        return defenseProperty().get();
    }

    public void setDefense(double defense) {
        defenseProperty().set(defense);
    }

    @Override
    public String toString() {
        return String.format("Defense: %s", getDefense());
    }

    @Override
    public boolean unique() {
        return true;
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }
}
