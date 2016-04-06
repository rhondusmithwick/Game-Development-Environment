package model.component.movement;

import javafx.beans.property.SimpleDoubleProperty;
import model.component.IComponent;
import utility.Pair;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity implements IComponent {

    private final Pair<SimpleDoubleProperty, SimpleDoubleProperty> pair;

    public Velocity() {
        pair = new Pair<>(new SimpleDoubleProperty(this, "speed", 0),
                new SimpleDoubleProperty(this, "direction", 0));
    }

    public Velocity(Double speed, Double direction) {
        this();
        setSpeed(speed);
        setDirection(direction);
    }

    public Velocity(Double vx, Double vy, boolean flag) {
        this();
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

    public double getVX() {
        return getSpeed() * Math.cos(Math.toRadians(getDirection()));
    }

    public double getVY() {
        return getSpeed() * Math.sin(Math.toRadians(getDirection()));
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
