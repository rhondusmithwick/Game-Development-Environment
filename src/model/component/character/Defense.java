package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import model.component.IComponent;
import utility.Unit;


/**
 * @author Roxanne Baker
 */
public class Defense implements IComponent {

    private final Unit<SimpleDoubleProperty> unit;

    public Defense() {
        unit = new Unit<>(new SimpleDoubleProperty(this, "defense", 0.0));
    }

    public Defense(Double defense) {
        this();
        setDefense(defense);
    }

    public SimpleDoubleProperty defenseProperty() {
        return unit._1();
    }

    public double getDefense() {
        return defenseProperty().get();
    }

    public void setDefense(double defense) {
        defenseProperty().set(defense);
    }

    @Override
    public String toString() {
        return String.format("Defense: %s", getDefense());
    }

    @Override
    public boolean unique() {
        return true;
    }
}
