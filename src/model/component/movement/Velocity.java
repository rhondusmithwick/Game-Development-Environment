package model.component.movement;

import api.IComponent;
import javafx.beans.property.SimpleDoubleProperty;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity implements IComponent {
    private final SimpleDoubleProperty speed = new SimpleDoubleProperty(this, "speed", 0);
    private final SimpleDoubleProperty direction = new SimpleDoubleProperty(this, "direction", 0);

    public Velocity() {
    }

    public Velocity(Double speed, Double direction) {
        setSpeed(speed);
        setDirection(direction);
    }

    public Velocity(Double vx, Double vy, boolean flag) {
        setSpeed(Math.sqrt(vx * vx + vy * vy));
        setDirection(Math.atan2(vy, vx));
    }

    public double getSpeed() {
        return speed.get();
    }

    public void setSpeed(double speed) {
        this.speed.set(speed);
    }

    public double getDirection() {
        return direction.get();
    }

    public void setDirection(double direction) {
        this.direction.set(direction);
    }

    public double getVX() {
        return getSpeed() * Math.cos(Math.toRadians(getDirection()));
    }

    public double getVY() {
        return getSpeed() * Math.sin(Math.toRadians(getDirection()));
    }

    public SimpleDoubleProperty speedProperty() {
        return speed;
    }

    public SimpleDoubleProperty directionProperty() {
        return direction;
    }
}
