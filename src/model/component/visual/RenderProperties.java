package model.component.visual;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.TwoProperty;

import java.util.List;

/**
 * Holds RenderProperties, which are Tint and Transparency.
 *
 * @author Rhondu Smithwick
 */
public class RenderProperties implements IComponent {

    private final TwoProperty<Double, Double> twoProperty = new TwoProperty<>("Tint", 0.0, "Transparency", 0.0);

    /**
     * Empty constructor. Both Start at 0.0.
     */
    public RenderProperties() {
    }

    /**
     * Initialize with initial values.
     *
     * @param tint         initial value for tint
     * @param transparency initial value for transparency
     */
    public RenderProperties(double tint, double transparency) {
        setTint(tint);
        setTransparency(transparency);
    }

    /**
     * Get the tint property.
     *
     * @return the tint property
     */
    public SimpleObjectProperty<Double> tintProperty() {
        return twoProperty.property1();
    }

    public Double getTint() {
        return tintProperty().get();
    }

    public void setTint(double tint) {
        tintProperty().set(tint);
    }

    /**
     * Get the transparency property.
     *
     * @return the transparency property
     */
    public SimpleObjectProperty<Double> transparencyProperty() {
        return twoProperty.property2();
    }

    public Double getTransparency() {
        return transparencyProperty().get();
    }

    public void setTransparency(double transparency) {
        transparencyProperty().set(transparency);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return twoProperty.getProperties();
    }
}
