package model.component.movement;

import model.component.DataComponent;

/**
 * Created by rhondusmithwick on 4/6/16.
 *
 * @author Rhondu Smithwick
 */
public class X extends DataComponent<Double> {

    public X() {
        super("X", 0.0);
    }

    public X(double x) {
        this();
        set(x);
    }
}
