package model.component.movement;

import javafx.beans.property.SimpleObjectProperty;
import model.component.base.Pair;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity extends Pair<Double, Double> {

    public Velocity() {
        super(0.0, 0.0);
    }

    public Velocity(Double speed, Double direction) {
        super(speed, direction);
    }

    public Velocity(Double vx, Double vy, boolean flag) {
        setVXY(vx, vy);
    }

    public double getSpeed() {
        return getValue1();
    }

    public void setSpeed(double speed) {
        setValue1(speed);
    }

    public double getDirection() {
        return getValue2();
    }

    public void setDirection(double direction) {
        setValue2(direction);
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

    public SimpleObjectProperty<Double> speedProperty() {
        return value1Property();
    }

    public SimpleObjectProperty<Double> directionProperty() {
        return value2Property();
    }

    @Override
    public String toString() {
        return String.format("Velocity: [Speed: %s, Direction: %s]", getSpeed(), getDirection());
    }
}
