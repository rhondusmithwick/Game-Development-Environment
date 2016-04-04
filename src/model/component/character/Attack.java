package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * @author Roxanne Baker
 */
public class Attack implements IComponent {

    private final SimpleDoubleProperty attack = new SimpleDoubleProperty(this, "attack", 0);

    public Attack(Double attack) {
        setAttack(attack);
    }

    public SimpleDoubleProperty attackProperty() {
        return attack;
    }

    public double getAttack() {
        return attack.get();
    }

    public void setAttack(double attack) {
        this.attack.set(attack);
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
