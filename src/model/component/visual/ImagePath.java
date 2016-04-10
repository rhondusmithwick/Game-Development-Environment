package model.component.visual;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import utility.SingleProperty;

import java.util.Collections;
import java.util.List;

/**
 * Component to hold an imagePath.
 *
 * @author Rhondu Smithwick
 */
public class ImagePath implements IComponent {

    /**
     * The singleProperty.
     */
    private final SingleProperty<String> singleProperty;

    /**
     * Construct with starting value.
     *
     * @param imagePath starting value
     */
    public ImagePath(String imagePath) {
        singleProperty = new SingleProperty<>("ImagePath", imagePath);
    }

    /**
     * Get the imagePath property.
     *
     * @return impagePath string property
     */
    public SimpleObjectProperty<String> imagePathProperty() {
        return singleProperty.property1();
    }


    public String getImagePath() {
        return imagePathProperty().get();
    }

    public void setImagePath(String imagePath) {
        this.imagePathProperty().set(imagePath);
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return Collections.singletonList(imagePathProperty());
    }
}
