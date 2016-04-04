package model.component.base;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class Triple<A, B, C> extends Pair<A, B>  {
    private final SimpleObjectProperty<C> value3;

    public Triple(String name1, String name2, String name3) {
        super(name1, name2);
        value3 = new SimpleObjectProperty<>(this, name3, null);
    }

    public Triple(String name1)
}
