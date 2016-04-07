package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;


/**
 * The Defense property. Holds a single double property.
 *
 * @author Roxanne Baker
 */
public class Defense implements IComponent {

    /**
     * The unit to contain the property.
     */
    private final Unit<SimpleDoubleProperty> unit = new Unit<>(new SimpleDoubleProperty(this, "defense", 0.0));

    /**
     * Construct to start defense at 0.
     */
    public Defense() {
    }

    /**
     * Construct with initial value.
     *
     * @param defense the initial defense value
     */
    public Defense(Double defense) {
        setDefense(defense);
    }

    /**
     * Get the defense property.
     *
     * @return the defense property
     */
    public SimpleDoubleProperty defenseProperty() {
        return unit._1();
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
}
