package model.component.movement;

import javafx.scene.image.Image;
import model.component.DataComponent;

import javax.swing.text.html.ImageView;

/**
 * Created by rhondusmithwick on 4/6/16.
 *
 * @author Rhondu Smithwick
 */
public class Y extends DataComponent<Double> {

    public Y() {
        super("Y", 0.0);
    }

    public Y(double y) {
        this();
        set(y);
    }

}
