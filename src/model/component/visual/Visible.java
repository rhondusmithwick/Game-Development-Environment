package model.component.visual;

import model.component.IComponent;
import javafx.beans.property.SimpleBooleanProperty;
import utility.Unit;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Visible implements IComponent {
    private final Unit<SimpleBooleanProperty> unit;

    public Visible() {
        unit = new Unit<>(new SimpleBooleanProperty(this, "visible", true));
    }

    public Visible(boolean visible) {
        this();
        setVisible(visible);
    }

    public boolean getVisible() {
        return visibleProperty().get();
    }

    public void setVisible(boolean visible) {
        visibleProperty().set(visible);
    }

    public SimpleBooleanProperty visibleProperty() {
        return unit._1();
    }
}
