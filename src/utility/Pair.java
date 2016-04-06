package utility;

/**
 * Created by rhondusmithwick on 4/3/16.
 * <p>
 * A Pair class that holds two values of type A and B.
 *
 * @author Rhondu Smithwick
 */
public class Pair<A, B> extends Unit<A> {

    private final B _2;

    public Pair(A _1, B _2) {
        super(_1);
        this._2 = _2;
    }

    public B _2() {
        return _2;
    }
}
