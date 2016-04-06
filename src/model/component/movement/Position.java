package model.component.movement;

import javafx.beans.property.SimpleDoubleProperty;
<<<<<<< HEAD
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
=======
import model.component.IComponent;
import utility.Triple;
>>>>>>> master


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Position implements IComponent {

    private final Triple<SimpleDoubleProperty, SimpleDoubleProperty, SimpleDoubleProperty> triple;

    public Position() {
        triple = new Triple<>(new SimpleDoubleProperty(this, "x", 0),
                new SimpleDoubleProperty(this, "y", 0),
                new SimpleDoubleProperty(this, "orientation", 0));
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
        return triple._1();
    }

    public double getY() {
        return triple._2().get();
    }

    public void setY(double y) {
        yProperty().set(y);
    }

    public SimpleDoubleProperty yProperty() {
        return triple._2();
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
        return triple._3();
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
