package animation;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
 * Created by rhondusmithwick on 4/24/16.
 *
 * @author Rhondu Smithwick
 */
public class RectangleLogic {
    private final List<Rectangle> rectangleList = new ArrayList<>();
    private final List<Double> widthList = new ArrayList<>();
    private final List<Double> heightList = new ArrayList<>();
    private final List<Double> yList = new ArrayList<>();
    private final List<Double> xList = new ArrayList<>();
    private final RectangleDrawer rectDrawer;
    private Rectangle selectedRectangle;

    public RectangleLogic(RectangleDrawer rectDrawer) {
        this.rectDrawer = rectDrawer;
    }

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

    public Map<String, String> getAnimationMap(double duration) {
        Map<String, String> moveAnimationMap = new HashMap<>();
        moveAnimationMap.put("duration", String.format("%.2f", duration));
        moveAnimationMap.put("xList", xList.toString());
        moveAnimationMap.put("yList", yList.toString());
        moveAnimationMap.put("width", widthList.toString());
        moveAnimationMap.put("height", heightList.toString());
        return moveAnimationMap;
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

    public Animation getAnimation(ImageView imageView, Duration duration) {
        return new ComplexAnimation(imageView, duration,
                rectangleList.size(), xList, yList, widthList, heightList);
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

    public Rectangle getSelectedRectangle() {
        return selectedRectangle;
    }

    public void setSelectedRectangle(Rectangle selectedRectangle) {
        this.selectedRectangle = selectedRectangle;
    }

    public List<Rectangle> getRectangleList() {
        return rectangleList;
    }
}
