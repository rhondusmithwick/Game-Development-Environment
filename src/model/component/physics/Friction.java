package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

/**
 * The friction component.
 *
 * @author Roxanne Baker
 */
@SuppressWarnings("serial")
public class Friction implements IComponent {

    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Friction", 0.6);

    /**
     * Empty constructor. Defaults to 9.81.
     */
    public Friction () {
    }

    /**
     * Construct with an initial value.
     *
     * @param friction the initial value
     */
    public Friction (double friction) {
        setFriction(friction);
    }

    /**
     * Get the friction property.
     *
     * @return the friction property
     */
    public SimpleObjectProperty<Double> frictionProperty () {
        return singleProperty.property1();
    }

    public double getFriction () {
        return frictionProperty().get();
    }

    public void setFriction (double friction) {
        frictionProperty().set(friction);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return singleProperty.getProperties();
    }

    @Override
    public void update () {
        setFriction(getFriction());
    }
}