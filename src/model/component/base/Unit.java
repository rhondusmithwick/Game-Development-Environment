package model.component.base;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Unit<A>  {

    private final SimpleObjectProperty<A> value1 = new SimpleObjectProperty<>(this, "value1", null);

    public Unit() {
    }

    public Unit(A value1) {
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
