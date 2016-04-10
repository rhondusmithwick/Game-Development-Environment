package utility;

/**
 * A Pair class that holds two final values of type A and B.
 *
 * @param <A> type of value 1
 * @param <B> type of value 2
 * @author Rhondu Smithwick
 */
public class Pair<A, B> extends Unit<A> {

    /**
     * Value 2.
     */
    private final B _2;

    /**
     * Construct a pair.
     *
     * @param _1 first value
     * @param _2 second value
     */
    public Pair(A _1, B _2) {
        super(_1);
        this._2 = _2;
    }

    /**
     * Return second value.
     *
     * @return second value
     */
    public B _2() {
        return _2;
    }
}
