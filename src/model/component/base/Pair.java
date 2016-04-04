package model.component.base;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Pair<A, B> extends Unit<A> {
    private final SimpleObjectProperty<B> value2 = new SimpleObjectProperty<>(this, "value2", null);

    public Pair() {
    }

    public Pair(A value1, B value2) {
        super(value1);
        setValue2(value2);
    }

    public B getValue2() {
        return value2.get();
    }

    public SimpleObjectProperty<B> value2Property() {
        return value2;
    }

    public void setValue2(B value2) {
        this.value2.set(value2);
    }
}
