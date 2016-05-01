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
@SuppressWarnings("serial")
public class Orientation implements IComponent {

    /**
     * Single Proprety.
     */
    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Orientation", 0.0);
    private String orientationString = "north";

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
     * Get the orientationString property.
     *
     * @return the orientationString property
     */
    public SimpleObjectProperty<Double> orientationProperty() {
        return singleProperty.property1();
    }

    public double getOrientation() {
        return orientationProperty().get();
    }

    public void setOrientation(double orientation) {
        orientationProperty().set(orientation);
        if (orientation < 90) {
            orientationString = "north";
        } else if (orientation < 180) {
            orientationString = "east";
        } else if (orientation < 270) {
            orientationString = "south";
        } else {
            orientationString = "west";
        }
    }

    public String getOrientationString() {
        return orientationString;
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }

    @Override
    public void update() {
        setOrientation(getOrientation());
    }
}
