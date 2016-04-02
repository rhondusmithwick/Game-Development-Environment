package model.component.base;

import java.io.Serializable;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class OneValueContainer<A> implements Serializable {

    private A value1;

    public OneValueContainer(A value1) {
        setValue1(value1);
    }

    protected A getValue1() {
        return value1;
    }

    protected void setValue1(A value1) {
        this.value1 = value1;
    }

    protected String toStringHelp(String name1) {
        return String.format("%s: %s", name1, getValue1());
    }

    @Override
    public String toString() {
        return String.format("Value1: %s", getValue1());
    }
}
