package model.component.movement;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

/**
 * Holds Orientation of an entiy.
 *
 * @author Rhondu Smithwick
 */
public class Orientation implements IComponent {

    /**
     * Single Proprety.
     */
    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Orientation", 0.0);

    /**
     * Empty constructor. Starts at 0.0.
     */
    public Orientation() { 
    }

    /**
     * Start with a default value.
     *
     * @param orientation the default value
     */
    public Orientation(double orientation) {
        setOrientation(orientation);
    }

    /**
     * Get the orientation property.
     *
     * @return the orientation property
     */
    public SimpleObjectProperty<Double> orientationProperty() {
        return singleProperty.property1();
    }

    public double getOrientation() {
        return orientationProperty().get();
    }

    public void setOrientation(double orientation) {
        orientationProperty().set(orientation);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }
}
