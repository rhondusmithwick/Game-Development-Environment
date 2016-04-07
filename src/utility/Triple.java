package utility;

/**
 * A Triple class that holds 3 final values of types A, B, and C.
 *
 * @param <A> type of value 1
 * @param <B> type of value 2
 * @param <C> type of value 3
 * @author Rhondu Smithwick
 */
public class Triple<A, B, C> extends Pair<A, B> {

    /**
     * Value 3.
     */
    private final C _3;

    /**
     * Construct a triple with three values.
     *
     * @param _1 first value
     * @param _2 second value
     * @param _3 third value
     */
    public Triple(A _1, B _2, C _3) {
        super(_1, _2);
        this._3 = _3;
    }

    /**
     * Get the third value.
     *
     * @return the thrid value
     */
    public C _3() {
        return _3;
    }
}
