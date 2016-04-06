package utility;

/**
 * Created by rhondusmithwick on 4/3/16.
 * <p>
 * A Pair class that holds two values of type A and B.
 *
 * @author Rhondu Smithwick
 */
public class Pair<A, B> extends Unit<A> {

    private final B value2;

    public Pair(A value1, B value2) {
        super(value1);
        this.value2 = value2;
    }

    public B _2() {
        return value2;
    }
}
