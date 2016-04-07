package model.component.character;

import javafx.beans.property.SimpleDoubleProperty;
import api.IComponent;
import utility.Unit;


/**
 * The class for Attack. Contains a single double property.
 * {@inheritDoc}
 *
 * @author Roxanne Baker
 */
public class Attack implements IComponent {

    /**
     * The unit to contain the property.
     */
    private final Unit<SimpleDoubleProperty> unit = new Unit<>(new SimpleDoubleProperty(this, "attack", 0.0));

    /**
     * Empty constructor. Has attack at 0.
     */
    public Attack() {
    }

    /**
     * Construct with an initial value.
     * @param attack the initial value
     */
    public Attack(Double attack) {
        setAttack(attack);
    }

    /**
     * Get the attack as a property.
     * @return the attack property
     */
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
