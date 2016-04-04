package model.component.character;

import model.component.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;

/**
 * @author Rhondu Smithwick
 */
public class Health extends Unit<SimpleDoubleProperty> implements IComponent {

    public Health() {
        setValue1(new SimpleDoubleProperty(this, "health", 0));
    }

    public Health(Double health) {
        this();
        setHealth(health);
    }

    public SimpleDoubleProperty healthProperty() {
        return getValue1();
    }

    public double getHealth() {
        return healthProperty().get();
    }

    public void setHealth(double health) {
        healthProperty().set(health);
    }
}
