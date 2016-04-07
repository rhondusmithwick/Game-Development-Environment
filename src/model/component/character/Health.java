package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;

/**
 * The health component.
 *
 * @author Rhondu Smithwick
 */
public class Health implements IComponent {

    /**
     * The unit for health.
     */
    private final Unit<SimpleDoubleProperty> unit = new Unit<>(new SimpleDoubleProperty(this, "health", 0.0));

    /**
     * Empty constructor. Defaults to 0.
     */
    public Health() {
    }

    /**
     * Construct with an initial value.
     *
     * @param health the initial value
     */
    public Health(Double health) {
        setHealth(health);
    }

    /**
     * Get the health property.
     *
     * @return the health property
     */
    public SimpleDoubleProperty healthProperty() {
        return unit._1();
    }

    public double getHealth() {
        return healthProperty().get();
    }

    public void setHealth(double health) {
        healthProperty().set(health);
    }

}
