package model.component.movement;

import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;
import api.IComponent;

public class MovementStatus implements IComponent{

	private final SingleProperty<String> singleProperty = new SingleProperty<>("MovementStatus", "");

    /**
     * Empty constructor. Has movementStatus at empty string.
     */
    public MovementStatus() {
    }

    /**
     * Construct with an initial value.
     *
     * @param attack the initial value
     */
    public MovementStatus(String movementStatus) {
        setMovementStatus(movementStatus);
    }

    /**
     * Get the attack as a property.
     *
     * @return the attack property
     */
    public SimpleObjectProperty<String> movementStatusProperty() {
        return singleProperty.property1();
    }

    public String getMovementStatus() {
        return movementStatusProperty().get();
    }

    public void setMovementStatus(String movementStatus) {
    	movementStatusProperty().set(movementStatus);
    }

    @Override
    public String toString() {
        return String.format("Movement Status: %s", getMovementStatus());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }

    @Override
    public void update() {
        setMovementStatus(getMovementStatus());
    }
}
