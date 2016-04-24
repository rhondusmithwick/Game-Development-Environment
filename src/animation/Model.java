package animation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/23/16.
 *
 * @author Rhondu Smithwick, Melissa Zhang
 */
public class Model {
    private final ImageView spriteImageView = new ImageView();
    private final ImageView previewImageView = new ImageView();
    private final Map<String, Map<String, String>> animationMap = new HashMap<>();
    private final RectangleDrawer rectDrawer = new RectangleDrawer();
    private Image spriteImage;

    public void saveAnimation(String name, Map<String, String> animationMap) {
        this.animationMap.put(name, animationMap);
    }

    public void changeColor(double x, double y) {
        Image image = new ColorChanger(spriteImage, x, y, Color.TRANSPARENT).changeImage();
        setSpriteImage(image);
    }

    public void resetRectangleDrawer() {
        rectDrawer.reset();
    }

    public void handleMousePressed(MouseEvent event) {
        rectDrawer.handleMousePressed(event);
    }

    public void handleMouseDragged(MouseEvent event) {
        rectDrawer.handleMouseDragged(event);
    }

    public Map<String, Map<String, String>> getAnimationMap() {
        return animationMap;
    }

    public ImageView getSpriteImageView() {
        return spriteImageView;
    }

    public ImageView getPreviewImageView() {
        return previewImageView;
    }

    public Rectangle getRectDrawer() {
        return rectDrawer.getRectangle();
    }

    public Image getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(Image spriteImage) {
        this.spriteImage = spriteImage;
        spriteImageView.setImage(spriteImage);
    }

    public RectangleDrawer getRectangleDrawer() {
        return rectDrawer;
    }

}
