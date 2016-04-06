package model.component.character;

import model.component.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;


/**
 * @author Roxanne Baker
 */
public class Attack extends Unit<SimpleDoubleProperty> implements IComponent {

    public Attack() {
        setValue1(new SimpleDoubleProperty(this, "attack", 0));
    }

    public Attack(Double attack) {
        this();
        setValue1(new SimpleDoubleProperty(this, "attack", 0));
    }

    public SimpleDoubleProperty attackProperty() {
        return getValue1();
    }

    public double getAttack() {
        return attackProperty().get();
    }

    public void setAttack(double attack) {
        getValue1().set(attack);
    }

    @Override
    public String toString() {
        return String.format("Attack: %s", getAttack());
    }

    @Override
    public boolean unique() {
        return true;
    }
}
