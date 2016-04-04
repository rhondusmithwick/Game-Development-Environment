package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * @author Roxanne Baker
 */
public class Defense implements IComponent {

    private final SimpleDoubleProperty attack = new SimpleDoubleProperty(this, "defense", 0);

    public Defense(Double defense) {
        setAttack(defense);
    }

    public SimpleDoubleProperty defenseProperty() {
        return defense;
    }

    public double getDefense() {
        return defense.get();
    }

    public void set(double attack) {
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
