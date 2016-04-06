package model.physics;

import model.component.IComponent;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Mass implements IComponent {
    private final SimpleDoubleProperty mass = new SimpleDoubleProperty(this, "mass");


    public Mass(double mass) {
        setMass(mass);
    }


    public double getMass() {
        return mass.get();
    }

    public void setMass(double mass) {
        assert mass != 0;
        this.mass.set(mass);
    }

    public SimpleDoubleProperty massProperty() {
        return mass;
    }
}
