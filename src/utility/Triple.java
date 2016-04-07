package utility;

/**
 * Created by rhondusmithwick on 4/3/16.
 * <p>
 * A Triple class that holds 3 values of types A, B, and C.
 *
 * @author Rhondu Smithwick
 */
public class Triple<A, B, C> extends Pair<A, B> {

    private final C _3;

    public Triple(A _1, B _2, C _3) {
        super(_1, _2);
        this._3 = _3;
    }

    public C _3() {
        return _3;
    }
}
