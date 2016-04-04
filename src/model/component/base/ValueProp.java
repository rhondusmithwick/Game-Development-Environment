package model.component.base;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class ValueProp<T> implements IComponent {
    private final SimpleObjectProperty<T> value = new SimpleObjectProperty<>(this, "val", null);

    public T getValue() {
        return value.get();
    }

    public SimpleObjectProperty<T> valueProperty() {
        return value;
    }

    public void setValue(T value) {
        this.value.set(value);
    }
}
