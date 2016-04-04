package model.component.movement;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import model.component.base.Triple;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Position extends Triple<Double, Double, Double> implements IComponent {

    public Position(Double x, Double y) {
        super(x, y, 0.0);
    }

    public Position(Double x, Double y, Double orientation) {
        this(x, y);
        setOrientation(orientation);
    }


    public double getX() {
        return getValue1();
    }

    public void setX(double x) {
        setValue1(x);
    }

    public SimpleObjectProperty<Double> xProperty() {
        return value1Property();
    }

    public double getY() {
        return getValue2();
    }

    public void setY(double y) {
        setValue2(y);
    }

    public SimpleObjectProperty<Double> yProperty() {
        return value2Property();
    }

    public void setXY(double x, double y) {
        setX(x);
        setY(y);
    }


    public double getOrientation() {
        return getValue3();
    }

    public void setOrientation(double orientation) {
        setValue3(orientation);
    }

    public SimpleObjectProperty<Double> orientationProperty() {
        return value3Property();
    }

    public void add(double dx, double dy) {
        this.setXY(getX() + dx, getY() + dy);
    }

    @Override
    public String toString() {
        return String.format("Position: [X: %s, Y: %s, Orientation: %s]", getX(), getY(), getOrientation());
    }

    @Override
    public boolean unique() {
        return true;
    }

}
