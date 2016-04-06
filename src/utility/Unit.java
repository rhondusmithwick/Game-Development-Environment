package utility;


/**
 * Created by rhondusmithwick on 4/3/16.
 * <p>
 * A Unit class that holds a single value of type A.
 *
 * @author Rhondu Smithwick
 */
public class Unit<A> {

    private final A value1;

    public Unit(A value1) {
        this.value1 = value1;
    }

    public A _1() {
        return value1;
    }

}
