package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import model.component.IComponent;
import utility.Unit;


/**
 * @author Roxanne Baker
 */
public class Attack implements IComponent {

    private final Unit<SimpleDoubleProperty> unit;

    public Attack() {
        unit = new Unit<>(new SimpleDoubleProperty(this, "attack", 0.0));
    }

    public Attack(Double attack) {
        this();
        setAttack(attack);
    }

    public SimpleDoubleProperty attackProperty() {
        return unit._1();
    }

    public double getAttack() {
        return attackProperty().get();
    }

    public void setAttack(double attack) {
        attackProperty().set(attack);
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