package model.component;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/6/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class DataComponent<T> implements IComponent {
    private final SimpleObjectProperty<T> property;

    protected DataComponent(String name, T initialValue) {
        property = new SimpleObjectProperty<>(this, name, initialValue);
    }

    public T get() {
        return property.get();
    }

    public void set(T newValue) {
        property.set(newValue);
    }

    public SimpleObjectProperty<T> property() {
        return property;
    }

}
