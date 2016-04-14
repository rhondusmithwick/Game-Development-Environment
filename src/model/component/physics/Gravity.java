package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

/**
 * The gravity component.
 *
 * @author Roxanne Baker
 */
public class Gravity implements IComponent {

    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Gravity", 9.81);

    /**
     * Empty constructor. Defaults to 9.81.
     */
    public Gravity() {
    	setGravity(100);
    }

    /**
     * Construct with an initial value.
     *
     * @param gravity the initial value
     */
    public Gravity(double gravity) {
        setGravity(gravity);
    }

    /**
     * Get the gravity property.
     *
     * @return the gravity property
     */
    public SimpleObjectProperty<Double> gravityProperty() {
        return singleProperty.property1();
    }

    public double getGravity() {
        return gravityProperty().get();
    }

    public void setGravity(double gravity) {
        gravityProperty().set(gravity);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }
}