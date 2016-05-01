package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.Collections;
import java.util.List;

/**
 * @author Roxanne Baker
 */
@SuppressWarnings("serial")
public class RestitutionCoefficient implements IComponent {

    private final SingleProperty<Double> singleProperty = new SingleProperty<>("CoefficientofRestitution", 0.0);

    public RestitutionCoefficient () {
    }

    public RestitutionCoefficient (double restitutionCoefficient) {
        setRestitutionCoefficient(restitutionCoefficient);
    }

    public SimpleObjectProperty<Double> restitutionCoefficientProperty () {
        return singleProperty.property1();
    }

    public double getRestitutionCoefficient () {
        return restitutionCoefficientProperty().get();
    }

    public void setRestitutionCoefficient (double restitutionCoefficient) {
        restitutionCoefficientProperty().set(restitutionCoefficient);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return Collections.singletonList(restitutionCoefficientProperty());
    }

    @Override
    public void update () {
        setRestitutionCoefficient(getRestitutionCoefficient());
    }

}
