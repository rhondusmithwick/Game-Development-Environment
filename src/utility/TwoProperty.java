package utility;

import javafx.beans.property.SimpleObjectProperty;

import java.util.Arrays;
import java.util.List;

/**
 * Holds Two Properties and allows access to them.
 *
 * @param <A> type of first property
 * @param <B> type of second property
 * @author Rhondu Smithwick
 */
public class TwoProperty<A, B> extends SingleProperty<A> {

    private final SimpleObjectProperty<B> property2;

    /**
     * Constructor.
     *
     * @param name1  of the first property
     * @param value1 starting value of the frist property
     * @param name2  of the second property
     * @param value2 starting value of the second property
     */
    public TwoProperty (String name1, A value1, String name2, B value2) {
        super(name1, value1);
        property2 = new SimpleObjectProperty<>(this, name2, value2);
    }

    /**
     * Get the second property.
     *
     * @return the second property
     */
    public SimpleObjectProperty<B> property2 () {
        return property2;
    }

    /**
     * Get the properties as a list.
     *
     * @return the properties as a list
     */
    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return Arrays.asList(property1(), property2());
    }
}
