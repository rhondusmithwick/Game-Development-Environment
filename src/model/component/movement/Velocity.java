package model.component.movement;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;
import utility.Pair;

import java.util.function.DoubleUnaryOperator;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity implements IComponent {

    private final Pair<SimpleDoubleProperty, SimpleDoubleProperty> pair = new Pair<>(new SimpleDoubleProperty(this, "speed", 0),
            new SimpleDoubleProperty(this, "direction", 0));
    ;

    public Velocity() {
    }

    public Velocity(Double speed, Double direction) {
        setSpeed(speed);
        setDirection(direction);
    }

    public Velocity(Double vx, Double vy, boolean flag) {
        setVXY(vx, vy);
    }

    public double getSpeed() {
        return speedProperty().get();
    }

    public void setSpeed(double speed) {
        speedProperty().set(speed);
    }

    public SimpleDoubleProperty speedProperty() {
        return pair._1();
    }

    public double getDirection() {
        return directionProperty().get();
    }

    public void setDirection(double direction) {
        directionProperty().set(direction);
    }

    public SimpleDoubleProperty directionProperty() {
        return pair._2();
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
}
