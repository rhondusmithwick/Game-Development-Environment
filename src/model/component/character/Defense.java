package model.component.character;

import model.component.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;


/**
 * @author Roxanne Baker
 */
public class Defense extends Unit<SimpleDoubleProperty> implements IComponent {

    public Defense() {
        setValue1(new SimpleDoubleProperty(this, "defense", 0));
    }

    public Defense(Double defense) {
        this();
        setDefense(defense);
    }

    public SimpleDoubleProperty defenseProperty() {
        return getValue1();
    }

    public double getDefense() {
        return defenseProperty().get();
    }

    public void setDefense(double defense) {
        defenseProperty().set(defense);
    }

    @Override
    public String toString() {
        return String.format("Attack: %s", getDefense());
    }

    @Override
    public boolean unique() {
        return true;
    }

}
