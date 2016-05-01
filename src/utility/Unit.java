package utility;


/**
 * A Unit class that holds a single final value of type A.
 *
 * @param <A> type of value 1
 * @author Rhondu Smithwick
 */
public class Unit<A> {

    /**
     * The first value.
     */
    private final A _1;

    /**
     * Construct a unit.
     *
     * @param _1 the first value.
     */
    public Unit (A _1) {
        this._1 = _1;
    }

    /**
     * Get first value.
     *
     * @return first value
     */
    public A _1 () {
        return _1;
    }

}
