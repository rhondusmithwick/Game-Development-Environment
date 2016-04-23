package animation;

import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import view.Dragger;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static animation.DoubleConstants.DURATION_DEFAULT;
import static animation.DoubleConstants.DURATION_MAX;
import static animation.DoubleConstants.DURATION_MIN;
import static animation.DoubleConstants.KEY_INPUT_SPEED;
import static animation.SaveHandler.saveAnimations;
import static animation.SaveHandler.saveImage;
import static animation.StringConstants.ADD_FRAME;
import static animation.StringConstants.ANIMATION_NAME_PROMPT;
import static animation.StringConstants.DELETE_FRAME;
import static animation.StringConstants.NEW_ANIMATION;
import static animation.StringConstants.NEW_SPRITE;
import static animation.StringConstants.NO_SELECT_EFFECT;
import static animation.StringConstants.PREVIEW_ANIMATION;
import static animation.StringConstants.SAVE_ANIMATION;
import static animation.StringConstants.SAVE_ANIMATIONS_TO_FILE;
import static animation.StringConstants.SELECT_EFFECT;
import static animation.UtilityUtilities.makeButton;

class SpriteUtility {
    private final SimpleObjectProperty<Boolean> changeColorProperty = new SimpleObjectProperty<>(this, "ChangeColor", false);

    private final GUI gui = new GUI(changeColorProperty);
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

    private Slider durationSlider;
    private Rectangle selectedRectangle;
    private Rectangle rectDrawer;

    private Image spriteImage;
    private String spriteSheetPath;

    public void init(Stage stage) {
        stage.setScene(gui.getScene());
        initializeGui();
    }

    private void initializeGui() {
        selectedRectangle = null;
        reinitializeGui();
        initButtons();
        gui.init();

    }

    private void reinitializeGui() {
        gui.getSpriteGroup().getChildren().clear();
        animationList.clear();
        initNewImage();
        initCanvas();
        initRectangleDrawer();
        reInitialize();
    }

    private void initButtons() {
        addButton(makeButton(SAVE_ANIMATIONS_TO_FILE.get(), e -> saveAnimations(spriteSheetPath, animationList)), gui.getButtonBox());
        addButton(makeButton(NEW_ANIMATION.get(), e -> reInitialize()), gui.getButtonBox());
        addButton(makeButton(NEW_SPRITE.get(), e -> reinitializeGui()), gui.getButtonBox());
        addButton(makeButton(PREVIEW_ANIMATION.get(), e -> animationPreview()), gui.getButtonBox());
        addButton(makeButton(SAVE_ANIMATION.get(), e -> saveAnimation()), gui.getButtonBox());
        addButton(makeButton(ADD_FRAME.get(), e -> addFrame()), gui.getButtonBox());
        addButton(makeButton(DELETE_FRAME.get(), e -> deleteFrame(selectedRectangle)), gui.getButtonBox());
        addButton(gui.getActivateTransparencyButton(), gui.getButtonBox());
        addButton(makeButton("Save Image", e -> saveImage(spriteImageView.getImage())), gui.getButtonBox());
    }

    private void addButton(Button button, VBox box) {
        button.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().add(button);
    }


    private void deleteFrame(Rectangle frameToDelete) {
        if (frameToDelete != null) {
            for (Rectangle existingRect : rectangleList) {
                if (existingRect == frameToDelete) {
                    if (selectedRectangle == frameToDelete) {
                        selectedRectangle = null;
                    }
                    gui.getSpriteGroup().getChildren().remove(frameToDelete);
                    rectangleList.remove(existingRect);
                    initAnimationProperties();

                    break;
                }
            }
        }
    }

    /*
     * Initialize all animation gui elements (i.e. left side of pane)
     */
    private void initAnimationProperties() {
        gui.getButtonBox().getChildren().remove(previewImageView);
        gui.getAnimationPropertiesBox().getChildren().clear();
        initAnimationNameAndDurationFields();
        rectangleList.stream().forEach(this::displayRectangleListProperties);
    }


    /*
     * Initialize sprite sheet gui properties like animation name and duration
     */
    private void initAnimationNameAndDurationFields() {
        gui.getAnimationName().setText(ANIMATION_NAME_PROMPT.get());
        Label durationTextLabel = new Label("Duration");
        Label durationValueLabel = new Label("0.0");
        durationSlider = UtilityUtilities.makeSlider((ov, old_val, new_val) -> {
            durationValueLabel.setText(String.format("%.2f", new_val.floatValue()));
            initAnimationPreview();
        }, DURATION_MIN.get(), DURATION_MAX.get(), DURATION_DEFAULT.get());
        gui.getAnimationPropertiesBox().getChildren().addAll(gui.getAnimationName(), durationTextLabel, durationSlider,
                durationValueLabel);
    }

    private void reInitialize() {
        gui.getSpriteGroup().getChildren().removeAll(rectangleList);
        rectangleList.clear();
        initAnimationProperties();
    }

    private void initNewImage() {
        spriteImage = initFileChooser();
        spriteImageView.setImage(spriteImage);
        gui.getSpriteGroup().getChildren().add(spriteImageView);
        gui.getCanvas().setHeight(spriteImageView.getBoundsInLocal().getWidth());
        gui.getCanvas().setWidth(spriteImageView.getBoundsInLocal().getHeight());
    }

    private void initCanvas() {
        gui.getCanvas().layoutXProperty().bind(spriteImageView.layoutXProperty());
        gui.getCanvas().layoutYProperty().bind(spriteImageView.layoutYProperty());
        initCanvasHandlers(gui.getCanvas());
        gui.getSpriteGroup().getChildren().add(gui.getCanvas());
    }

    private void initCanvasHandlers(Canvas canvas2) {
        canvas2.setOnMouseDragged(this::mouseDragged);
        canvas2.setOnMousePressed(this::mousePressed);
        canvas2.setOnMouseReleased(this::mouseReleased);
        canvas2.setOnMouseClicked(this::mouseClicked);
    }

    private void animationPreview() {
        populateRectanglePropertyLists();
        initAnimationPreview();
    }

    private void initAnimationPreview() {
        gui.getButtonBox().getChildren().remove(previewImageView);
        previewImageView.setImage(spriteImage);
        Animation animation = new ComplexAnimation(previewImageView, Duration.millis(durationSlider.getValue()),
                rectangleList.size(), xList, yList, widthList, heightList);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        gui.getButtonBox().getChildren().add(previewImageView);
    }


    private void saveAnimation() {
        populateRectanglePropertyLists();
        Map<String, String> moveAnimationMap = new HashMap<>();
        String name = gui.getAnimationName().getText();
        moveAnimationMap.put("duration", String.format("%.2f", durationSlider.getValue()));
        moveAnimationMap.put("xList", xList.toString());
        moveAnimationMap.put("yList", yList.toString());
        moveAnimationMap.put("width", widthList.toString());
        moveAnimationMap.put("height", heightList.toString());
        animationList.put(name, moveAnimationMap);
    }

    /*
     * 
     */
    private void populateRectanglePropertyLists() {
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

    private void addFrame() {
        Rectangle clone = cloneRect(rectDrawer);
        rectangleList.add(clone);
        addRectangleToDisplay(clone);
    }

    private void addRectangleToDisplay(Rectangle clone) {
        gui.getSpriteGroup().getChildren().add(clone);
        displayRectangleListProperties(clone);
    }

    /*
     * Puts properties of all frames on GUI i.e. x, y, width, height; Makes them editable
     */
    private void displayRectangleListProperties(Rectangle clone) {
        List<String> propertyList = Arrays.asList("x", "y", "width", "height");
        for (String propertyName : propertyList) {
            Label label = new Label(propertyName);
            TextField field = new TextField();
            field.setMinWidth(50);
            gui.getAnimationPropertiesBox().getChildren().addAll(label, field);
            try {
                Method method = clone.getClass().getMethod(propertyName + "Property");
                DoubleProperty rectProperty = (DoubleProperty) method.invoke(clone);
                StringConverter<Number> converter = new NumberStringConverter();
                Bindings.bindBidirectional(field.textProperty(), rectProperty, converter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Rectangle cloneRect(Rectangle rect) {
        Rectangle r = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.RED);
        Dragger.makeDraggable(r);
        makeSelectable(r);
        return r;
    }

    private void makeSelectable(Rectangle r) {
        r.setOnMouseClicked(e -> makeSelected(r));
    }

    private void makeSelected(Rectangle r) {
        addSelectEffect(r);
        selectedRectangle = r;
        if (rectDrawer != selectedRectangle) {
            removeSelectEffect(rectDrawer);
        }
        Predicate<Rectangle> notSelected = (rect) -> (rect != selectedRectangle);
        rectangleList.stream().filter(notSelected).forEach(this::removeSelectEffect);
    }


    private void addSelectEffect(Rectangle img) {
        img.setStyle(SELECT_EFFECT.get());
    }

    private void removeSelectEffect(Rectangle imageView) {
        imageView.setStyle(NO_SELECT_EFFECT.get());
    }

    /*
     * Initializes initial rectangle drawer
     */
    private Rectangle initRectangleDrawer() {
        gui.getSpriteGroup().getChildren().remove(rectDrawer);
        rectDrawer = new Rectangle();
        rectDrawer.widthProperty().unbind();
        rectDrawer.heightProperty().unbind();
        rectinitX.set(0.0);
        rectinitY.set(0.0);
        rectX.set(0.0);
        rectY.set(0.0);
        rectDrawer.widthProperty().bind(rectX.subtract(rectinitX));
        rectDrawer.heightProperty().bind(rectY.subtract(rectinitY));
        rectDrawer.setFill(Color.TRANSPARENT);
        rectDrawer.setStroke(Color.BLACK);
        gui.getSpriteScroll().requestFocus(); //ugh someone fix this
        gui.getSpriteScroll().setOnKeyPressed(this::keyPress); //this line keeps fucking up
        makeSelected(rectDrawer);
        gui.getSpriteGroup().getChildren().add(rectDrawer);
        return rectDrawer;
    }

    private void keyPress(KeyEvent event) {
        KeyCode keycode = event.getCode();
        switch (keycode) {
            case ENTER:
                addFrame();
                break;
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
        event.consume();
    }

    private Image initFileChooser() {
        File spriteSheet = UtilityUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"),
                "Choose a spritesheet");
        spriteSheetPath = spriteSheet.toURI().toString();
        return new Image(spriteSheetPath);
    }


    private void mouseReleased(MouseEvent event) {
        rectDrawer.widthProperty().unbind();
        rectDrawer.heightProperty().unbind();
        Dragger.makeDraggable(rectDrawer);
        spriteImageView.setCursor(Cursor.DEFAULT);
        if (changeColorProperty.get()) {
            gui.getCanvas().setCursor(Cursor.CROSSHAIR);
        } else {
            gui.getCanvas().setCursor(Cursor.DEFAULT);
        }


    }

    private void mousePressed(MouseEvent event) {
        if (!changeColorProperty.get()) {
            gui.getCanvas().setCursor(Cursor.CLOSED_HAND);
            if (event.getButton() == MouseButton.PRIMARY) {
                rectDrawer = initRectangleDrawer();
                rectDrawer.setX(event.getX());
                rectDrawer.setY(event.getY());
                rectinitX.set(event.getX());
                rectinitY.set(event.getY());
            } else if (event.getButton() == MouseButton.SECONDARY) {
                rectDrawer.setX(event.getX());
                rectDrawer.setY(event.getY());
            }
        }
    }

    private void mouseDragged(MouseEvent event) {
        rectX.set(event.getX());
        rectY.set(event.getY());
    }

    private void mouseClicked(MouseEvent event) {
        if (changeColorProperty.get()) {
            double x = event.getX();
            double y = event.getY();
            spriteImage = new ColorChanger(spriteImageView.getImage(), x, y, Color.TRANSPARENT).changeImage();
            spriteImageView.setImage(spriteImage);
        }
    }

}
