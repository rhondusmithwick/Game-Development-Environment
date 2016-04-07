package model.component.movement;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import utility.Pair;
import utility.TwoProperty;

import java.util.List;
import java.util.function.DoubleUnaryOperator;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity implements IComponent {

    private final TwoProperty<Double, Double> twoProperty;

    public Velocity() {
        twoProperty = new TwoProperty<>("Spped", 0.0, "Direction", 0.0);
    }

    public Velocity(double speed, double direction) {
        this();
        setSpeed(speed);
        setDirection(direction);
    }

    public Velocity(double vx, double vy, boolean flag) {
        this();
        setVXY(vx, vy);
    }

    public double getSpeed() {
        return speedProperty().get();
    }

    public void setSpeed(double speed) {
        speedProperty().set(speed);
    }

    public SimpleObjectProperty<Double> speedProperty() {
        return twoProperty.property1();
    }

    public double getDirection() {
        return directionProperty().get();
    }

    public void setDirection(double direction) {
        directionProperty().set(direction);
    }

    public SimpleObjectProperty<Double> directionProperty() {
        return twoProperty.property2();
    }

    private double getVHelp(DoubleUnaryOperator func) {
        double directionRadians = Math.toRadians(getDirection());
        return getSpeed() * func.applyAsDouble(directionRadians);
    }

    public double getVX() {
        return getVHelp(Math::cos);
    }

    public double getVY() {
        return getVHelp(Math::sin);
    }

    public void setVXY(double vx, double vy) {
        setSpeed(Math.sqrt(vx * vx + vy * vy));
        setDirection(Math.toDegrees(Math.atan2(vy, vx)));
    }

    public void add(double dvx, double dvy) {
        setVXY(getVX() + dvx, getVY() + dvy);
    }

    @Override
    public String toString() {
        return String.format("Velocity: [Speed: %s, Direction: %s]", getSpeed(), getDirection());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return twoProperty.getProperties();
    }
}
