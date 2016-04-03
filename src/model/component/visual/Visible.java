package model.component.visual;

import api.IComponent;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Visible implements IComponent {
    private final SimpleBooleanProperty visible = new SimpleBooleanProperty(this, "visible", true);


    public Visible(boolean visible) {
        setVisible(visible);
    }

    public boolean getVisible() {
        return visible.get();
    }

    public SimpleBooleanProperty visibleProperty() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible.set(visible);
    }
}
