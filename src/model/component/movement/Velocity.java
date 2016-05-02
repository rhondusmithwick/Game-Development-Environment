package model.component.movement;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.TwoProperty;

import java.util.List;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
@SuppressWarnings("serial")
public class Velocity implements IComponent {

    private final TwoProperty<Double, Double> twoProperty = new TwoProperty<>("XVelocity", 0.0, "YVelocity", 0.0);

    public Velocity () {
    }

    public Velocity (double vx, double vy) {
        setVXY(vx, vy);
    }

    public Velocity (double speed, double direction, boolean flag) {
        this();
        setSpeed(speed);
        setDirection(direction);
    }

    public double getSpeed () {
        return Math.sqrt(Math.pow(getVX(), 2) + Math.pow(getVY(), 2));
    }

    public void setSpeed (double speed) {
//		double ratio = speed / getSpeed();
//		setVX(getVX() * ratio);
//		setVY(getVY() * ratio);
        setVXY(speed * Math.cos(getDirection()), speed * Math.sin(getDirection()));
    }

    public double getDirection () {
        return Math.atan2(getVX(), getVY());
    }

    public void setDirection (double direction) {
//		double ratio = Math.tan(direction) / (getVX() / getVY());
//		setVXY(getVX() * ratio, getVY() * ratio);
        setVXY(getSpeed() * Math.cos(direction), getSpeed() * Math.sin(direction));
    }

    public SimpleObjectProperty<Double> vxProperty () {
        return twoProperty.property1();
    }

    public double getVX () {
        return vxProperty().get();
    }

    public void setVX (double vx) {
        vxProperty().set(vx);
    }

    public SimpleObjectProperty<Double> vyProperty () {
        return twoProperty.property2();
    }

    public double getVY () {
        return vyProperty().get();
    }

    public void setVY (double vy) {
        vyProperty().set(vy);
    }

    // private double getVHelp(DoubleUnaryOperator func) {
    // double directionRadians = Math.toRadians(getDirection());
    // return getSpeed() * func.applyAsDouble(directionRadians);
    // }

    public void setVXY (double vx, double vy) {
        setVX(vx);
        setVY(vy);
    }

    public void add (double dvx, double dvy) {
        setVXY(getVX() + dvx, getVY() + dvy);
    }

    @Override
    public String toString () {
        return String.format("Velocity: [X: %s, Y: %s]", getVX(), getVY());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return twoProperty.getProperties();
    }

    @Override
    public void update () {
        setVX(getVX());
        setVY(getVY());
    }
}
