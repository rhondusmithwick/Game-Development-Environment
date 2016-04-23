package animation;

import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Dimension2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
import java.util.TreeMap;
import java.util.function.Predicate;

import static animation.SaveHandler.saveImage;

class SpriteUtility {
    private static final String SAVE_ANIMATIONS_TO_FILE = "Save Animations to File";
    private static final String NEW_ANIMATION = "New Animation";
    private static final String NEW_SPRITE = "New Sprite";
    private static final String PREVIEW_ANIMATION = "Preview Animation";
    private static final String SAVE_ANIMATION = "Save Animation";
    private static final String ADD_FRAME = "Add Frame";
    private static final String DELETE_FRAME = "Delete Frame";

    private static final Dimension2D dimensions = new Dimension2D(800, 600);

    private static final double DURATION_MIN = 100;
    private static final double DURATION_MAX = 3000;
    private static final double DURATION_DEFAULT = 1000;
    private static final double KEY_INPUT_SPEED = 5;

    private static final String SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,50,0.8), 10, 0, 0, 0)";
    private static final String NO_SELECT_EFFECT = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0), 0, 0, 0, 0)";

    private final SimpleObjectProperty<Boolean> changeColorProperty = new SimpleObjectProperty<>(this, "ChangeColor", false);
    private final BorderPane mainPane = new BorderPane();
    private final VBox animationPropertiesBox = new VBox();
    private final VBox buttonBox = new VBox();
    private final Group spriteGroup = new Group();
    private final ScrollPane spriteScroll = new ScrollPane(spriteGroup);
    private final Scene scene = new Scene(mainPane, dimensions.getWidth(), dimensions.getHeight());

    private final Map<String, Map> animationList = new HashMap<>();
    private final List<Rectangle> rectangleList = new ArrayList<>();

    private ImageView spriteImageView;
    private ImageView previewImageView;

    private TextField animationName;

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

    private Button activateTransparencyButton;
    private Canvas canvas;
    private Image spriteImage;

    public void init(Stage stage) {
        stage.setScene(scene);
        initializeGui();
    }

    private void initializeGui() {
        selectedRectangle = null;

        initNewImage();
        initRectangleDrawer();
        initAnimationProperties();
        initButtons();

        mainPane.setCenter(spriteScroll);
        mainPane.setRight(new ScrollPane(buttonBox));
        mainPane.setLeft(new ScrollPane(animationPropertiesBox));
    }

    private void initButtons() {
        addButton(UtilityUtilities.makeButton(SAVE_ANIMATIONS_TO_FILE, e -> reInitialize()), buttonBox);
        addButton(UtilityUtilities.makeButton(NEW_ANIMATION, e -> reInitialize()), buttonBox);
        addButton(UtilityUtilities.makeButton(NEW_SPRITE, e -> initializeGui()), buttonBox);
        addButton(UtilityUtilities.makeButton(PREVIEW_ANIMATION, e -> animationPreview()), buttonBox);
        addButton(UtilityUtilities.makeButton(SAVE_ANIMATION, e -> saveAnimation()), buttonBox);
        addButton(UtilityUtilities.makeButton(ADD_FRAME, e -> addFrame()), buttonBox);
        addButton(UtilityUtilities.makeButton(DELETE_FRAME, e -> deleteFrame(selectedRectangle)), buttonBox);
        activateTransparencyButton = UtilityUtilities.makeButton("Activate Transparency", e -> makeTransparent());
        addButton(activateTransparencyButton, buttonBox);
        addButton(UtilityUtilities.makeButton("Save Image", e -> saveImage(spriteImageView.getImage())), buttonBox);
    }

    private void addButton(Button button, VBox box) {
        button.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().add(button);
    }

    private void makeTransparent() {
        if (changeColorProperty.get()) {
            activateTransparencyButton.setText("Activate Transparency");
            changeColorProperty.set(false);
            canvas.setCursor(Cursor.DEFAULT);
        } else {
            activateTransparencyButton.setText("Deactivate Transparency");
            changeColorProperty.set(true);
            canvas.setCursor(Cursor.CROSSHAIR);
        }
    }

    private void deleteFrame(Rectangle frameToDelete) {
        if (frameToDelete != null) {
            for (Rectangle existingRect : rectangleList) {
                if (existingRect == frameToDelete) {
                    if (selectedRectangle == frameToDelete) {
                        selectedRectangle = null;
                    }
                    spriteGroup.getChildren().remove(frameToDelete);
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
        buttonBox.getChildren().remove(previewImageView);
        animationPropertiesBox.getChildren().clear();
        initAnimationNameAndDurationFields();
        rectangleList.stream().forEach(this::displayRectangleListProperties);
    }


    /*
     * Initialize sprite sheet gui properties like animation name and duration
     */
    private void initAnimationNameAndDurationFields() {
        animationName = new TextField("Animation Name");
        Label durationTextLabel = new Label("Duration");
        Label durationValueLabel = new Label("0.0");
        durationSlider = UtilityUtilities.makeSlider((ov, old_val, new_val) -> {
            durationValueLabel.setText(String.format("%.2f", new_val.floatValue()));
            initAnimationPreview();
        }, DURATION_MIN, DURATION_MAX, DURATION_DEFAULT);
        animationPropertiesBox.getChildren().addAll(animationName, durationTextLabel, durationSlider,
                durationValueLabel);
    }

    private void reInitialize() {
        spriteGroup.getChildren().removeAll(rectangleList);
        rectangleList.clear();
        animationList.clear();
        initAnimationProperties();
    }

    private void initNewImage() {
        spriteImage = initFileChooser();
        spriteImageView = new ImageView(spriteImage);
        spriteGroup.getChildren().add(spriteImageView);
        canvas = new Canvas(spriteImageView.getBoundsInParent().getWidth(), spriteImageView.getBoundsInParent().getHeight());
        canvas.layoutXProperty().set(spriteImageView.getLayoutX());
        canvas.layoutYProperty().set(spriteImageView.getLayoutY());

        initCanvasHandlers(canvas);
        spriteGroup.getChildren().add(canvas);

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
        buttonBox.getChildren().remove(previewImageView);
        previewImageView = new ImageView(spriteImage);
        Animation animation = new ComplexAnimation(previewImageView, Duration.millis(durationSlider.getValue()),
                rectangleList.size(), xList, yList, widthList, heightList);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        buttonBox.getChildren().add(previewImageView);
    }

    private void saveAnimation() {
        populateRectanglePropertyLists();
        Map<String, String> moveAnimationMap = new TreeMap<>();
        String name = animationName.getText();
        moveAnimationMap.put("duration", String.format("%.2f", durationSlider.getValue()));
        moveAnimationMap.put("xList", xList.toString());
        moveAnimationMap.put("yList", yList.toString());
        moveAnimationMap.put("width", widthList.toString());
        moveAnimationMap.put("height", heightList.toString());
        animationList.put(name, moveAnimationMap);
        System.out.println(animationList);
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
        spriteGroup.getChildren().add(clone);
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
            animationPropertiesBox.getChildren().addAll(label, field);
            try {
                Method method = clone.getClass().getMethod(propertyName + "Property");
                StringConverter<Number> converter = new NumberStringConverter();
                DoubleProperty rectProperty;
                rectProperty = (DoubleProperty) method.invoke(clone);
                Bindings.bindBidirectional(field.textProperty(), rectProperty, converter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Rectangle cloneRect(Rectangle rect) {
        Rectangle r = new Rectangle();
        r.setX(rect.getX());
        r.setY(rect.getY());
        r.setWidth(rect.getWidth());
        r.setHeight(rect.getHeight());
        r.setFill(Color.TRANSPARENT);
        r.setStroke(Color.RED);
        Dragger.makeDraggable(r);
        makeSelectable(r);
        return r;
    }

    private void makeSelectable(Rectangle r) {
        r.setOnMouseClicked(e -> makeSelectable(r));
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
        img.setStyle(SELECT_EFFECT);
    }

    private void removeSelectEffect(Rectangle imageView) {
        imageView.setStyle(NO_SELECT_EFFECT);
    }

    /*
     * Initializes initial rectangle drawer
     */
    private Rectangle initRectangleDrawer() {
        spriteGroup.getChildren().remove(rectDrawer);
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
        spriteScroll.requestFocus(); //ugh someone fix this
        spriteScroll.setOnKeyPressed(this::keyPress); //this line keeps fucking up
        makeSelected(rectDrawer);
        spriteGroup.getChildren().add(rectDrawer);


        return rectDrawer;
    }

    private void keyPress(KeyEvent event) {
        KeyCode keycode = event.getCode();
        switch (keycode) {
            case ENTER:
                addFrame();
                break;
            case RIGHT:
                selectedRectangle.setX(selectedRectangle.getX() + KEY_INPUT_SPEED);
                break;
            case LEFT:
                selectedRectangle.setX(selectedRectangle.getX() - KEY_INPUT_SPEED);
                break;
            case UP:
                selectedRectangle.setY(selectedRectangle.getY() - KEY_INPUT_SPEED);
                break;
            case DOWN:
                selectedRectangle.setY(selectedRectangle.getY() + KEY_INPUT_SPEED);
                break;
            default:
        }
        event.consume();
    }

    private Image initFileChooser() {
        File spriteSheet = UtilityUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"),
                "Choose a spritesheet");
        return new Image(spriteSheet.toURI().toString());
    }


    private void mouseReleased(MouseEvent event) {
        rectDrawer.widthProperty().unbind();
        rectDrawer.heightProperty().unbind();
        Dragger.makeDraggable(rectDrawer);
        spriteImageView.setCursor(Cursor.DEFAULT);
        if (changeColorProperty.get()) {
            canvas.setCursor(Cursor.CROSSHAIR);
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }


    }

    private void mousePressed(MouseEvent event) {
        if (!changeColorProperty.get()) {
            canvas.setCursor(Cursor.CLOSED_HAND);
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
