package model.component.visual;

import model.component.IComponent;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by rhondusmithwick on 4/3/16.
 *
 * @author Rhondu Smithwick
 */
public class ImagePath implements IComponent {
    private final SimpleStringProperty imagePath = new SimpleStringProperty(this, "imagePath", "");

    public ImagePath(String imagePath) {
        setImagePath(imagePath);
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public SimpleStringProperty imagePathProperty() {
        return imagePath;
    }
}
