package utility;

import javafx.beans.property.SimpleObjectProperty;

import java.util.Collections;
import java.util.List;
import java.util.Observable;

import events.Trigger;

/**
 * Holds one property of type A.
 *
 * @param <A> the type of the first property
 * @author Rhondu Smithwick
 */
public class SingleProperty<A> {

    private final SimpleObjectProperty<A> property1;

    /**
     * Constructor.
     *
     * @param name1  of the property
     * @param value1 initial value of the property
     */
    public SingleProperty(String name1, A value1) {
        property1 = new SimpleObjectProperty<>(this, name1, value1);
    }

    /**
     * Get first property.
     *
     * @return the first property
     */
    public SimpleObjectProperty<A> property1() {
        return property1;
    }

    /**
     * Get the property as a list.
     *
     * @return the property as a list
     */
    public List<SimpleObjectProperty<?>> getProperties() {
        return Collections.singletonList(property1());
    }
}
