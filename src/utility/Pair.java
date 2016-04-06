package utility;

/**
 * Created by rhondusmithwick on 4/3/16.
 * <p>
 * A Pair class that holds two values of type A and B.
 *
 * @author Rhondu Smithwick
 */
public class Pair<A, B> extends Unit<A> {

    private B value2;

    protected Pair() {
    }

    public Pair(A value1, B value2) {
        super(value1);
        setValue2(value2);
    }

    public B getValue2() {
        return value2;
    }

    public void setValue2(B value2) {
        this.value2 = value2;
    }
}
