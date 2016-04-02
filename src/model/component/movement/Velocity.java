package model.component.movement;

import model.component.base.TwoDoubleComponent;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity extends TwoDoubleComponent {
    public Velocity() {
        super();
    }

    public Velocity(Double value1, Double value2) {
        super(value1, value2);
    }

    public Velocity(String[] args) {
        super(args);
    }

    public Double getVelocity() {
        return getValue1();
    }

    public void setVelocity(Double velocity) {
        setValue1(velocity);
    }

    public Double getAcceleration() {
        return getValue2();
    }

    public void setAcceleration(Double acceleration) {
        setValue2(acceleration);
    }

    @Override
    public String toString() {
        return toStringHelp("Velocity", "Acceleration");
    }


    @Override
    public boolean unique() {
        return true;
    }
}
