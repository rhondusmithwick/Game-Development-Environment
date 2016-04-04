package model.component.base;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Unit<A> implements IComponent {
    private final SimpleObjectProperty<A> value1;

    public Unit(String name1) {
        value1 = new SimpleObjectProperty<>(this, name1, null);
    }

    public Unit(String name1, A value1) {
        this(name1);
        setValue1(value1);
    }

    public A getValue1() {
        return value1.get();
    }

    public SimpleObjectProperty<A> value1Property() {
        return value1;
    }

    public void setValue1(A value1) {
        this.value1.set(value1);
    }
}
