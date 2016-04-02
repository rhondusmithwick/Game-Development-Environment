package model.component.base;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class TwoDoubleComponent extends TwoValueContainer<Double, Double> implements Component {

    protected TwoDoubleComponent() {
        super(0.0, 0.0);
    }

    protected TwoDoubleComponent(Double value1, Double value2) {
        super(value1, value2);
    }

    protected TwoDoubleComponent(String[] args) {
        this();
        setBothWithStrings(args);
    }

    public void setBothWithStrings(String[] args) {
        Double value1 = Double.parseDouble(args[0]);
        Double value2 = Double.parseDouble(args[1]);
        setBoth(value1, value2);
    }


}
