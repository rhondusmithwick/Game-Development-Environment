package utility;

/**
 * Created by rhondusmithwick on 4/3/16.
 * <p>
 * A Triple class that holds 3 values of types A, B, and C.
 *
 * @author Rhondu Smithwick
 */
public class Triple<A, B, C> extends Pair<A, B> {

    private final C value3;

    public Triple(A value1, B value2, C value3) {
        super(value1, value2);
        this.value3 = value3;
    }

    public C _3() {
        return value3;
    }
}
