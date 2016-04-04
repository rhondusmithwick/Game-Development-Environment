package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.component.base.Unit;


/**
 * @author Roxanne Baker
 */
public class Defense extends Unit<Double> implements IComponent {


    public Defense(Double defense) {
        setDefense(defense);
    }

    public SimpleObjectProperty<Double> defenseProperty() {
        return value1Property();
    }

    public double getDefense() {
        return getValue1();
    }

    public void setDefense(double defense) {
        setValue1(defense);
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
