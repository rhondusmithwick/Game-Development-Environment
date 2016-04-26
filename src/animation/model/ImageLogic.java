package animation.model;

import animation.colorchange.BFSColorChanger;
import animation.utility.GUIUtilities;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * This class holds the images and the logic on them.
 *
 * @author Rhondu Smithwick
 */
public class ImageLogic {
    private final ImageView spriteImageView = new ImageView();
    private final ImageView previewImageView = new ImageView();
    private Image spriteImage;
    private String spriteSheetPath;

    /**
     * Change the color to transparent of all places with the color at (x, y).
     *
     * @param x the starting x-coordinate
     * @param y the starting y-coordinate
     */
    public void changeColor(double x, double y) {
        Image image = new BFSColorChanger(spriteImage, x, y, Color.TRANSPARENT).changeImage();
        setSpriteImage(image);
    }

    /**
     * Add a new image using a file choser.
     */
    public void initNewImage() {
        Image newImage = initFileChooser();
        setSpriteImage(newImage);
    }

    private Image initFileChooser() {
        File spriteSheet = GUIUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"),
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
    	previewImageView.setImage(getSpriteImage());
        return previewImageView;
    }

    public Image getSpriteImage() {
        return spriteImage;
    }

    private void setSpriteImage(Image spriteImage) {
        this.spriteImage = spriteImage;
        spriteImageView.setImage(spriteImage);
    }

}
