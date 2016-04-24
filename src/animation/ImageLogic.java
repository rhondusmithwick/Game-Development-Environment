package animation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/23/16.
 *
 * @author Rhondu Smithwick, Melissa Zhang
 */
public class ImageLogic {
    private final ImageView spriteImageView = new ImageView();
    private final ImageView previewImageView = new ImageView();
    private Image spriteImage;
    private String spriteSheetPath;

    public void changeColor(double x, double y) {
        Image image = new ColorChanger(spriteImage, x, y, Color.TRANSPARENT).changeImage();
        setSpriteImage(image);
    }

    public void initNewImage() {
        Image newImage = initFileChooser();
        setSpriteImage(newImage);
    }

    private Image initFileChooser() {
        File spriteSheet = UtilityUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"),
                "Choose a spritesheet");
        spriteSheetPath = spriteSheet.toURI().toString();
        return new Image(spriteSheetPath);
    }

    public String getSpriteSheetPath() {
        return spriteSheetPath;
    }

    public ImageView getSpriteImageView() {
        return spriteImageView;
    }

    public ImageView getPreviewImageView() {
        return previewImageView;
    }

    public Image getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(Image spriteImage) {
        this.spriteImage = spriteImage;
        spriteImageView.setImage(spriteImage);
    }

}
