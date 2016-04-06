package model.component.visual;

import model.component.IComponent;
import javafx.beans.property.SimpleStringProperty;
import utility.Unit;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class ImagePath implements IComponent {

    private final Unit<SimpleStringProperty> unit;

    private ImagePath() {
        unit = new Unit<>(new SimpleStringProperty(this, "imagePath", ""));
    }

    public ImagePath(String imagePath) {
        this();
        setImagePath(imagePath);
    }

    public String getImagePath() {
        return imagePathProperty().get();
    }

    public void setImagePath(String imagePath) {
        this.imagePathProperty().set(imagePath);
    }

    public SimpleStringProperty imagePathProperty() {
        return unit._1();
    }
}
