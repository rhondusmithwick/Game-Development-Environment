package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import api.IComponent;
import utility.Unit;

/**
 * @author Rhondu Smithwick
 */
public class Health implements IComponent {

    private final Unit<SimpleDoubleProperty> unit = new Unit<>(new SimpleDoubleProperty(this, "health", 0.0));

    public Health() {
    }

    public Health(Double health) {
        setHealth(health);
    }

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
