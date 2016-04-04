package model.component.base;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Pair<A, B> extends Unit<A> {
    private final SimpleObjectProperty<B> value2;

    public Pair(String name1, String name2) {
        super(name1);
        value2 = new SimpleObjectProperty<B>(this, name2, null);
    }

    public Pair(String name1, A value1, String name2, B value2) {
        this(name1, name2);
        setValue1(value1);
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
