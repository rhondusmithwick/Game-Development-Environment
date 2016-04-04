package model.component.movement;

import model.component.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Triple;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Position extends Triple<SimpleDoubleProperty, SimpleDoubleProperty, SimpleDoubleProperty> implements IComponent {

    public Position() {
        setValue1(new SimpleDoubleProperty(this, "x", 0));
        setValue2(new SimpleDoubleProperty(this, "y", 0));
        setValue3(new SimpleDoubleProperty(this, "orientation", 0));
    }

    public Position(Double x, Double y) {
        this();
        setXY(x, y);
        setOrientation(0);
    }

    public Position(Double x, Double y, Double orientation) {
        this(x, y);
        setOrientation(orientation);
    }

    public double getX() {
        return xProperty().get();
    }

    public void setX(double x) {
        xProperty().set(x);
    }

    public SimpleDoubleProperty xProperty() {
        return getValue1();
    }

    public double getY() {
        return getValue2().get();
    }

    public void setY(double y) {
        yProperty().set(y);
    }

    public SimpleDoubleProperty yProperty() {
        return getValue2();
    }

    public void setXY(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getOrientation() {
        return orientationProperty().get();
    }

    public void setOrientation(double orientation) {
        orientationProperty().set(orientation);
    }

    public SimpleDoubleProperty orientationProperty() {
        return getValue3();
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
