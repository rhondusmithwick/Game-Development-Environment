package utility;

/**
 * A Quad class that holds 4 final values of types A, B, C, and D.
 *
 * @param <A> type of value 1
 * @param <B> type of value 2
 * @param <C> type of value 3
 * @param <D> type of value 4
 * @author Ben Zhang
 */
public class Quad<A, B, C, D> extends Triple<A, B, C> {

    /**
     * Value 4.
     */
    private final D _4;

    /**
     * Construct a quad with four values.
     *
     * @param _1 first value
     * @param _2 second value
     * @param _3 third value
     * @param _4 fourth value
     */
    public Quad (A _1, B _2, C _3, D _4) {
        super(_1, _2, _3);
        this._4 = _4;
    }

    /**
     * Get the fourth value.
     *
     * @return the fourth value
     */
    public D _4 () {
        return _4;
    }
}
