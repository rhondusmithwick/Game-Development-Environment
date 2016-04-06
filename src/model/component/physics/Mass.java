package model.component.physics;

import model.component.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Unit;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Mass extends Unit<SimpleDoubleProperty> implements IComponent {

    public Mass() {
        setValue1(new SimpleDoubleProperty(this, "mass", 0.0));
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
        return getValue1();
    }
}
