package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;

/**
 * Contains the drag, a single double property.
 *
 * @author Rhondu Smithwick
 */
public class Drag implements IComponent {
    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Beta", 0.0);

    /**
     * Empty constructor that starts at 0.
     */
    public Drag() {
    }

    /**
     * Start with an initial value.
     *
     * @param beta the initial value
     */
    public Drag(double beta) {
        setBeta(beta);
    }

    /**
     * Get the beta property.
     *
     * @return the beta property
     */
    public SimpleObjectProperty<Double> betaProperty() {
        return singleProperty.property1();
    }

    public Double getBeta() {
        return betaProperty().get();
    }

    public void setBeta(double beta) {
        betaProperty().set(beta);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }
}
