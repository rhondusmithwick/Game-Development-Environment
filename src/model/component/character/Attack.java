package model.component.character;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.List;


/**
 * The class for Attack. Contains a single double property.
 *
 * @author Roxanne Baker, Rhondu Smithwick
 */
public class Attack implements IComponent {


    private final SingleProperty<Double> singleProperty;

    /**
     * Empty constructor. Has attack at 0.
     */
    public Attack() {
        singleProperty = new SingleProperty<>("Attack", 0.0);
    }

    /**
     * Construct with an initial value.
     *
     * @param attack the initial value
     */
    public Attack(double attack) {
        this();
        setAttack(attack);
    }

    /**
     * Get the attack as a property.
     *
     * @return the attack property
     */
    public SimpleObjectProperty<Double> attackProperty() {
        return singleProperty.property1();
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

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return singleProperty.getProperties();
    }
}