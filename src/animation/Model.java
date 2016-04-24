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
    private final List<Rectangle> rectangleList = new ArrayList<>();
    private final List<Double> widthList = new ArrayList<>();
    private final List<Double> heightList = new ArrayList<>();
    private final List<Double> yList = new ArrayList<>();
    private final List<Double> xList = new ArrayList<>();
    private final RectangleDrawer rectDrawer = new RectangleDrawer();
    private Image spriteImage;
    private Rectangle selectedRectangle;

    public void populateRectanglePropertyLists() {
        xList.clear();
        yList.clear();
        widthList.clear();
        heightList.clear();
        for (Rectangle rect : rectangleList) {
            xList.add(rect.getX());
            yList.add(rect.getY());
            widthList.add(rect.getWidth());
            heightList.add(rect.getHeight());
        }
    }

    public Animation getPreviewAnimation(Duration duration) {
        previewImageView.setImage(spriteImage);
        Animation animation = new ComplexAnimation(previewImageView, duration,
                rectangleList.size(), xList, yList, widthList, heightList);
        animation.setCycleCount(Animation.INDEFINITE);
        return animation;
    }

    public void saveAnimation(String name, Double duration) {
        populateRectanglePropertyLists();
        Map<String, String> moveAnimationMap = new HashMap<>();
        moveAnimationMap.put("duration", String.format("%.2f", duration));
        moveAnimationMap.put("xList", xList.toString());
        moveAnimationMap.put("yList", yList.toString());
        moveAnimationMap.put("width", widthList.toString());
        moveAnimationMap.put("height", heightList.toString());
        animationMap.put(name, moveAnimationMap);
    }

    public boolean removeRectangle(Rectangle rectangle) {
        Iterator<Rectangle> iter = rectangleList.iterator();
        while (iter.hasNext()) {
            Rectangle next = iter.next();
            if (next == rectangle) {
                if (selectedRectangle == rectangle) {
                    selectedRectangle = null;
                }
                iter.remove();
                return true;
            }
        }
        return false;
    }

    public void handleKeyInput(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                selectedRectangle.setX(selectedRectangle.getX() + KEY_INPUT_SPEED.get());
                break;
            case LEFT:
                selectedRectangle.setX(selectedRectangle.getX() - KEY_INPUT_SPEED.get());
                break;
            case UP:
                selectedRectangle.setY(selectedRectangle.getY() - KEY_INPUT_SPEED.get());
                break;
            case DOWN:
                selectedRectangle.setY(selectedRectangle.getY() + KEY_INPUT_SPEED.get());
                break;
            default:
        }
    }

    public void changeColor(double x, double y) {
        Image image = new ColorChanger(spriteImage, x, y, Color.TRANSPARENT).changeImage();
        setSpriteImage(image);
    }

    public void makeSelected(Rectangle r) {
        addSelectEffect(r);
        selectedRectangle = r;
        if (rectDrawer.getRectangle() != selectedRectangle) {
            removeSelectEffect(rectDrawer.getRectangle());
        }
        Predicate<Rectangle> notSelected = (rect) -> (rect != selectedRectangle);
        rectangleList.stream().filter(notSelected).forEach(this::removeSelectEffect);
    }

    public Rectangle cloneRect(Rectangle rect) {
        Rectangle r = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.SKYBLUE);
        Dragger.makeDraggable(r);
        makeSelectable(r);
        return r;
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

    private void makeSelectable(Rectangle r) {
        r.setOnMouseClicked(e -> makeSelected(r));
    }

    private void addSelectEffect(Rectangle rectangle) {
        rectangle.setStyle(SELECT_EFFECT.get());
        rectangle.setStroke(Color.RED);
    }

    private void removeSelectEffect(Rectangle rectangle) {
        rectangle.setStyle(NO_SELECT_EFFECT.get());
        rectangle.setStroke(Color.SKYBLUE);
    }

    public Map<String, Map<String, String>> getAnimationMap() {
        return animationMap;
    }

    public List<Rectangle> getRectangleList() {
        return rectangleList;
    }

    public ImageView getSpriteImageView() {
        return spriteImageView;
    }

    public ImageView getPreviewImageView() {
        return previewImageView;
    }

    public Rectangle getSelectedRectangle() {
        return selectedRectangle;
    }

    public void setSelectedRectangle(Rectangle selectedRectangle) {
        this.selectedRectangle = selectedRectangle;
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
