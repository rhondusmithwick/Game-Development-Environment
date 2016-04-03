package model.component.movement;

import java.util.Arrays;
import java.util.List;
import model.component.base.Value;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Velocity extends Value<List<Double>> {
    public Velocity () {
        super(Arrays.asList(0.0, 0.0));
    }

    public Velocity (Double speed, Double direction) {
        super(Arrays.asList(speed, direction));
    }

    public Velocity (Double vx, Double vy, boolean flag) {
        super(Arrays.asList(Math.sqrt(vx * vx + vy * vy), Math.atan2(vy, vx)));
    }

    public double getSpeed () {
        return getValue().get(0);
    }

    public void setSpeed (double speed) {
        this.getValue().set(0, speed);
    }

    public double getDirection () {
        return this.getValue().get(1);
    }

    public void setDirection (double direction) {
        this.getValue().set(1, direction);
    }

    public double getVX () {
        return this.getSpeed() * Math.cos(Math.toRadians(this.getDirection()));
    }

    public double getVY () {
        return this.getSpeed() * Math.sin(Math.toRadians(this.getDirection()));
    }

}
