package model.component.base;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Triple<A, B, C> extends Pair<A, B>  {
    private final SimpleObjectProperty<C> value3 = new SimpleObjectProperty<>(this, "value3", null);

    public Triple() {
    }
    
    public Triple(A value1, B value2, C value3) {
        super(value1, value2);
        setValue3(value3);
    }

    public C getValue3() {
        return value3.get();
    }

    public SimpleObjectProperty<C> value3Property() {
        return value3;
    }

    public void setValue3(C value3) {
        this.value3.set(value3);
    }
}
