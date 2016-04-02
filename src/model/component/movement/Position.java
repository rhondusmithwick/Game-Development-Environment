package model.component.movement;

import model.component.base.TwoDoubleComponent;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Position extends TwoDoubleComponent {
    public Position() {
        super();
    }

    public Position(String[] args) {
        super(args);
    }

    public Position(Double value1, Double value2) {
        super(value1, value2);
    }

    public Double getX() {
        return getValue1();
    }

    public void setX(Double x) {
        setValue1(x);
    }

    public Double getY() {
        return getValue2();
    }

    public void setY(Double y) {
        setValue2(y);
    }

    @Override
    public String toString() {
        return "Position: [" + toStringHelp("X", "Y") + "]";
    }

    @Override
    public boolean unique() {
        return true;
    }
}
