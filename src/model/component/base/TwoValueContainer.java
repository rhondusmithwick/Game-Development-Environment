package model.component.base;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class TwoValueContainer<A, B> extends OneValueContainer<A> {
    private B value2;

    public TwoValueContainer(A value1, B value2) {
        super(value1);
        setValue2(value2);
    }

    protected B getValue2() {
        return value2;
    }

    protected void setValue2(B value2) {
        this.value2 = value2;
    }

    public void setBoth(A value1, B value2) {
        setValue1(value1);
        setValue2(value2);
    }

    @Override
    public String toString() {
        return String.format("%s, Value2: %s", super.toString(), getValue2());
    }

    protected String toStringHelp(String name1, String name2) {
        return String.format("%s, %s: %s: %s", name1, getValue1(), name2, getValue2());
    }
}
