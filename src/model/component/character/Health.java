package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

/**
 * The health component.
 *
 * @author Rhondu Smithwick
 */
public class Health implements IComponent {

    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Health", 0.0);

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
    public Health(double health) {
        setHealth(health);
    }

    /**
     * Get the health property.
     *
     * @return the health property
     */
    public SimpleObjectProperty<Double> healthProperty() {
        return singleProperty.property1();
    }

    public double getHealth() {
        return healthProperty().get();
    }

    public void setHealth(double health) {
        healthProperty().set(health);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }

    @Override
    public void update() {
        setHealth(getHealth());
    }
}