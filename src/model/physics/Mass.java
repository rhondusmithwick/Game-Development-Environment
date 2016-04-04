package model.physics;

import api.IComponent;
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

    public SimpleDoubleProperty massProperty() {
        return mass;
    }

    public void setMass(double mass) {
        assert mass != 0;
        this.mass.set(mass);
    }
}
