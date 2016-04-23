package animation;

import javafx.animation.Animation;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
 * @author Rhondu Smithwick
 */
public class Model {
    private final Map<String, Map<String, String>> animationList = new HashMap<>();
    private final List<Rectangle> rectangleList = new ArrayList<>();
    private final ImageView spriteImageView = new ImageView();
    private final ImageView previewImageView = new ImageView();

    private final List<Double> widthList = new ArrayList<>();
    private final List<Double> heightList = new ArrayList<>();
    private final List<Double> yList = new ArrayList<>();
    private final List<Double> xList = new ArrayList<>();
    private final DoubleProperty rectinitX = new SimpleDoubleProperty(this, "rectinitX", 0.0);
    private final DoubleProperty rectinitY = new SimpleDoubleProperty(this, "rectinitY", 0.0);
    private final DoubleProperty rectX = new SimpleDoubleProperty(this, "rectX", 0.0);
    private final DoubleProperty rectY = new SimpleDoubleProperty(this, "rectY", 0.0);

    private Rectangle selectedRectangle;
    private Rectangle rectDrawer;

    private Image spriteImage;

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
        animationList.put(name, moveAnimationMap);
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

    public void handleArrowKey(KeyEvent event) {
        switch(event.getCode()) {
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
        Image image =  new ColorChanger(spriteImage, x, y, Color.TRANSPARENT).changeImage();
        setSpriteImage(image);
    }

    public void setSpriteImage(Image spriteImage) {
        this.spriteImage = spriteImage;
        spriteImageView.setImage(spriteImage);
    }


    public void makeSelected(Rectangle r) {
        addSelectEffect(r);
        setSelectedRectangle(r);
        if (getRectDrawer() != getSelectedRectangle()) {
            removeSelectEffect(getRectDrawer());
        }
        Predicate<Rectangle> notSelected = (rect) -> (rect != getSelectedRectangle());
        getRectangleList().stream().filter(notSelected).forEach(this::removeSelectEffect);
    }


    public Rectangle cloneRect(Rectangle rect) {
        Rectangle r = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.RED);
        Dragger.makeDraggable(r);
        makeSelectable(r);
        return r;
    }

//    public void resetRectangleDrawer() {
//        this.rectDrawer = new Rectangle();
//        rectinitX.set(0.0);
//        rectinitY.set(0.0);
//        rectX.set(0.0);
//        rectY.set(0.0);
//        rectDrawer.widthProperty().bind(rectX.subtract(rectinitX));
//        rectDrawer.heightProperty().bind(rectY.subtract(rectinitY));
//        rectDrawer.setFill(Color.TRANSPARENT);
//        rectDrawer.setStroke(Color.BLACK);
//        makeSelected(rectDrawer);
//    }
//
//    public void handleMousePressed(MouseEvent event) {
//        if (event.getButton() == MouseButton.PRIMARY) {
//            resetRectangleDrawer();
//            rectDrawer.setX(event.getX());
//            rectDrawer.setY(event.getY());
//            rectinitX.set(event.getX());
//            rectinitY.set(event.getY());
//        } else if (event.getButton() == MouseButton.SECONDARY) {
//            rectDrawer.setX(event.getX());
//            rectDrawer.setY(event.getY());
//        }
//    }

    public void handleMouseDragged(MouseEvent event) {
        rectX.set(event.getX());
        rectY.set(event.getY());
    }

    private void makeSelectable(Rectangle r) {
        r.setOnMouseClicked(e -> makeSelected(r));
    }

    private void addSelectEffect(Rectangle img) {
        img.setStyle(SELECT_EFFECT.get());
    }

    private void removeSelectEffect(Rectangle imageView) {
        imageView.setStyle(NO_SELECT_EFFECT.get());
    }


    public Map<String, Map<String, String>> getAnimationList() {
        return animationList;
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

    public List<Double> getWidthList() {
        return widthList;
    }

    public List<Double> getHeightList() {
        return heightList;
    }

    public List<Double> getyList() {
        return yList;
    }

    public List<Double> getxList() {
        return xList;
    }

    public double getRectinitX() {
        return rectinitX.get();
    }

    public DoubleProperty rectinitXProperty() {
        return rectinitX;
    }

    public double getRectinitY() {
        return rectinitY.get();
    }

    public DoubleProperty rectinitYProperty() {
        return rectinitY;
    }

    public double getRectX() {
        return rectX.get();
    }

    public DoubleProperty rectXProperty() {
        return rectX;
    }

    public double getRectY() {
        return rectY.get();
    }

    public DoubleProperty rectYProperty() {
        return rectY;
    }

    public Rectangle getSelectedRectangle() {
        return selectedRectangle;
    }

    public void setSelectedRectangle(Rectangle selectedRectangle) {
        this.selectedRectangle = selectedRectangle;
    }

    public void setRectDrawer(Rectangle rectDrawer) {
        this.rectDrawer = rectDrawer;
    }

    public Rectangle getRectDrawer() {
        return rectDrawer;
    }

    public Image getSpriteImage() {
        return spriteImage;
    }

}
