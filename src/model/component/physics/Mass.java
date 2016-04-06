package model.component.physics;

import javafx.beans.property.SimpleDoubleProperty;
import model.component.IComponent;
import utility.Unit;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Mass implements IComponent {

    private final Unit<SimpleDoubleProperty> unit;

    public Mass() {
        unit = new Unit<>(new SimpleDoubleProperty(this, "mass", 0.0));
    }

    public Mass(double mass) {
        this();
        setMass(mass);
    }


    public double getMass() {
        return massProperty().get();
    }

    public void setMass(double mass) {
        assert mass != 0;
        massProperty().set(mass);
    }

    public SimpleDoubleProperty massProperty() {
        return unit.getValue1();
    }
}
