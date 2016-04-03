package model.component.movement;

import java.util.Arrays;
import java.util.List;
import model.component.base.Value;


/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class Position extends Value<List<Double>> {

    public Position () {
        super(Arrays.asList(0.0, 0.0));
    }

    public Position (Double x, Double y) {
        super(Arrays.asList(x, y));
    }

    public double getX () {
        return this.getValue().get(0);
    }

    public double getY () {
        return this.getValue().get(1);
    }

    public void setXY (double x, double y) {
        List<Double> pos = this.getValue();
        pos.set(0, x);
        pos.set(1, y);
    }

    public void add (double dx, double dy) {
        this.setXY(getX() + dx, getY() + dy);
    }

    // @Override
    // public String toString () {
    // return "Position: [" + toStringHelp("X", "Y") + "]";
    // }

    @Override
    public boolean unique () {
        return true;
    }
}
