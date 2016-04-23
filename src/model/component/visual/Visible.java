package model.component.visual;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.Collections;
import java.util.List;

/**
 * Visible Component for whether an Entity is visible or not.
 *
 * @author Rhondu Smithwick
 */
public class Visible implements IComponent {
    /**
     * Single Property.
     */
    private final SingleProperty<Boolean> singleProperty;

    /**
     * Empty Constructor. Starts as true.
     */
    public Visible() {
        singleProperty = new SingleProperty<>("Visible", true);
    }

    /**
     * Construct with a initial value.
     *
     * @param visible initial value
     */
    public Visible(boolean visible) {
        this();
        setVisible(visible);
    }

    /**
     * Get the visible property.
     *
     * @return the visible property.
     */
    public SimpleObjectProperty<Boolean> visibleProperty() {
        return singleProperty.property1();
    }


    public boolean getVisible() {
        return visibleProperty().get();
    }


    public void setVisible(boolean visible) {
        visibleProperty().set(visible);
        
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return Collections.singletonList(visibleProperty());
    }
}
