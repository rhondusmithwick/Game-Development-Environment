package model.component.movement;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.TwoProperty;

import java.util.List;
import java.util.function.DoubleUnaryOperator;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity implements IComponent {

    private final TwoProperty<Double, Double> twoProperty = new TwoProperty<>("X Velocity", 0.0, "Y Velocity", 0.0);

    public Velocity() {
    }

    public Velocity(double vx, double vy) {
        setVXY(vx, vy);
    }

    public Velocity(double speed, double direction, boolean flag) {
        this();
        setSpeed(speed);
        setDirection(direction);
    }

    public void setSpeed(double speed) {
        double ratio = speed / getSpeed();
        setVX(getVX()*ratio);
        setVY(getVY()*ratio);
    }
    
    public void setDirection(double direction) {
    	double ratio = Math.tan(direction) / (getVX() / getVY());
    	setVXY(getVX()*ratio, getVY()*ratio);
    }
    
    public double getSpeed() {
    	return Math.sqrt(Math.pow(getVX(), 2)+Math.pow(getVY(), 2));
    }
    
    public double getDirection() {
    	return Math.atan(getVX() / getVY());
    }

    public SimpleObjectProperty<Double> vxProperty() {
        return twoProperty.property1();
    }
    
    public double getVX() {
        return vxProperty().get();
    }

    public void setVX(double vx) {
        vxProperty().set(vx);
    }
    
    public SimpleObjectProperty<Double> vyProperty() {
        return twoProperty.property2();
    }

    public double getVY() {
        return vyProperty().get();
    }

    public void setVY(double vy) {
        vyProperty().set(vy);
    }

//    private double getVHelp(DoubleUnaryOperator func) {
//        double directionRadians = Math.toRadians(getDirection());
//        return getSpeed() * func.applyAsDouble(directionRadians);
//    }

    public void setVXY(double vx, double vy) {
        setVX(vx);
        setVY(vy);
    }

    public void add(double dvx, double dvy) {
        setVXY(getVX() + dvx, getVY() + dvy);
    }

    @Override
    public String toString() {
        return String.format("Velocity: [X: %s, Y: %s]", getSpeed(), getDirection());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return twoProperty.getProperties();
    }
}
