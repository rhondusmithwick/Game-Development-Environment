package model.component.base;

/**
 * Created by rhondusmithwick on 4/1/16.
 *
 * @author Rhondu Smithwick
 */
public class OneBooleanComponent extends OneValueContainer<Boolean> implements Component {

    public OneBooleanComponent() {
        super(true);
    }

    public OneBooleanComponent(Boolean value1) {
        super(value1);
    }

    public OneBooleanComponent(String[] args) {
        super(Boolean.valueOf(args[0]));
    }

}
