package model.component.physics;

import api.IComponent;
import com.google.common.base.Preconditions;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.Collections;
import java.util.List;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Mass implements IComponent {

    private final SingleProperty<Double> singleProperty = new SingleProperty<>("Mass", 1.0);

    public Mass() {
    }

    public Mass(double mass) {
        setMass(mass);
    }

    public SimpleObjectProperty<Double> massProperty() {
        return singleProperty.property1();
    }

    public double getMass() {
        return massProperty().get();
    }

    public void setMass(double mass) {
        boolean valid = mass > 0;
        Preconditions.checkArgument(valid, "Mass not greater than 0");
        massProperty().set(mass);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return Collections.singletonList(massProperty());
    }

    @Override
    public void update() {
        setMass(getMass());
    }
}
