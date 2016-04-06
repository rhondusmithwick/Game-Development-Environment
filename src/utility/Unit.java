package utility;


/**
 * Created by rhondusmithwick on 4/3/16.
 * <p>
 * A Unit class that holds a single value of type A.
 *
 * @author Rhondu Smithwick
 */
public class Unit<A> {

    private A value1;

    public Unit() {
    }

    public Unit(A value1) {
        setValue1(value1);
    }

    public A getValue1() {
        return value1;
    }


    public void setValue1(A value1) {
        this.value1 = value1;
    }
}
