package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.TwoProperty;

import java.util.List;

/**
 * Component for force. Contains a magnitude and a direction.
 *
 * @author Rhondu Smithwick
 */
public class GlobalForce implements IComponent {
    /**
     * The two property which will hold the magnitude and the direction.
     */
    private final TwoProperty<Double, Double> twoProperty = new TwoProperty<>("Magnitude", 0.0, "Direction", 0.0);

    /**
     * Default constructor. Starts with both at 0.0.
     */
    public GlobalForce() {
    }

    /**
     * Construct with initial values.
     *
     * @param magnitude initial magnitude
     * @param direction initial direction
     */
    public GlobalForce(double magnitude, double direction) {
        setMagnitude(magnitude);
        setDirection(direction);
    }

    /**
     * Get the magnitude property.
     *
     * @return the magnitude property
     */
    public SimpleObjectProperty<Double> magnitudeProperty() {
        return twoProperty.property1();
    }

    public Double getMagnitude() {
        return magnitudeProperty().get();
    }

    public void setMagnitude(double magnitude) {
        magnitudeProperty().set(magnitude);
    }

    /**
     * Get the direction property.
     *
     * @return the direction property
     */
    public SimpleObjectProperty<Double> directionProperty() {
        return twoProperty.property2();
    }

    public Double getDirection() {
        return directionProperty().get();
    }

    public void setDirection(double direction) {
        directionProperty().set(direction);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return twoProperty.getProperties();
    }

}
