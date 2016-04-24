package animation;

import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view.Dragger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static animation.DoubleConstants.KEY_INPUT_SPEED;
import static animation.StringConstants.NO_SELECT_EFFECT;
import static animation.StringConstants.SELECT_EFFECT;

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
    private final RectangleLogic logic = new RectangleLogic(rectDrawer);
    private Image spriteImage;

    public void populateRectanglePropertyLists() {
        logic.populateRectanglePropertyLists();
    }

    public Animation getPreviewAnimation(Duration duration) {
        previewImageView.setImage(spriteImage);
        Animation animation = logic.getAnimation(previewImageView, duration);
        animation.setCycleCount(Animation.INDEFINITE);
        return animation;
    }

    public void saveAnimation(String name, Double duration) {
        populateRectanglePropertyLists();
        Map<String, String> moveAnimationMap = logic.getAnimationMap(duration);
        animationMap.put(name, moveAnimationMap);
    }

    public boolean removeRectangle(Rectangle rectangle) {
        return logic.removeRectangle(rectangle);
    }

    public void handleKeyInput(KeyEvent event) {
        logic.handleKeyInput(event);
    }

    public void changeColor(double x, double y) {
        Image image = new ColorChanger(spriteImage, x, y, Color.TRANSPARENT).changeImage();
        setSpriteImage(image);
    }

    public void makeSelected(Rectangle r) {
        logic.makeSelected(r);
    }

    public Rectangle cloneRect(Rectangle rect) {
        return logic.cloneRect(rect);
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

    public List<Rectangle> getRectangleList() {
        return logic.getRectangleList();
    }

    public ImageView getSpriteImageView() {
        return spriteImageView;
    }

    public ImageView getPreviewImageView() {
        return previewImageView;
    }

    public Rectangle getSelectedRectangle() {
        return logic.getSelectedRectangle();
    }

    public void setSelectedRectangle(Rectangle selectedRectangle) {
        logic.setSelectedRectangle(selectedRectangle);
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

}
