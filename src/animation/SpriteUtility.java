package animation;

import javafx.animation.Animation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static animation.DoubleConstants.KEY_INPUT_SPEED;
import static animation.SaveHandler.saveAnimations;
import static animation.SaveHandler.saveImage;
import static animation.StringConstants.ADD_FRAME;
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
    private final Model model = new Model();
    private String spriteSheetPath;

    public void init(Stage stage) {
        stage.setScene(gui.getScene());
        initializeGui();
    }

    private void initializeGui() {
        model.setSelectedRectangle(null);
        reinitializeGui();
        initButtons();
        gui.init();

    }

    private void reinitializeGui() {
        gui.getSpriteGroup().getChildren().clear();
        model.getAnimationList().clear();
        initNewImage();
        initCanvas();
        initRectangleDrawer();
        reInitialize();
    }

    private void initButtons() {
        addButton(makeButton(SAVE_ANIMATIONS_TO_FILE.get(), e -> saveAnimations(spriteSheetPath, model.getAnimationList())), gui.getButtonBox());
        addButton(makeButton(NEW_ANIMATION.get(), e -> reInitialize()), gui.getButtonBox());
        addButton(makeButton(NEW_SPRITE.get(), e -> reinitializeGui()), gui.getButtonBox());
        addButton(makeButton(PREVIEW_ANIMATION.get(), e -> animationPreview()), gui.getButtonBox());
        addButton(makeButton(SAVE_ANIMATION.get(), e -> model.saveAnimation(gui.getAnimationName().getText(), gui.getDurationSlider().getValue())), gui.getButtonBox());
        addButton(makeButton(ADD_FRAME.get(), e -> addFrame()), gui.getButtonBox());
        addButton(makeButton(DELETE_FRAME.get(), e -> deleteFrame(model.getSelectedRectangle())), gui.getButtonBox());
        addButton(gui.getActivateTransparencyButton(), gui.getButtonBox());
        addButton(makeButton("Save Image", e -> saveImage(model.getSpriteImage())), gui.getButtonBox());
    }

    private void addButton(Button button, VBox box) {
        button.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().add(button);
    }


    private void deleteFrame(Rectangle frameToDelete) {
        if (frameToDelete != null) {
            boolean removed = model.removeRectangle(frameToDelete);
            if (removed) {
                gui.getSpriteGroup().getChildren().remove(frameToDelete);
                initAnimationProperties();
            }
        }
    }

    /*
     * Initialize all animation gui elements (i.e. left side of pane)
     */
    private void initAnimationProperties() {
        gui.getButtonBox().getChildren().remove(model.getPreviewImageView());
        gui.getAnimationPropertiesBox().getChildren().clear();
        gui.initAnimationNameAndDurationFields(this);
        model.getRectangleList().stream().forEach(this::displayRectangleListProperties);
    }


    private void reInitialize() {
        gui.getSpriteGroup().getChildren().removeAll(model.getRectangleList());
        model.getRectangleList().clear();
        initAnimationProperties();
    }

    private void initNewImage() {
        model.setSpriteImage(initFileChooser());
        ImageView spriteImageView = model.getSpriteImageView();
        gui.getSpriteGroup().getChildren().add(spriteImageView);
        gui.getCanvas().setHeight(spriteImageView.getBoundsInLocal().getWidth());
        gui.getCanvas().setWidth(spriteImageView.getBoundsInLocal().getHeight());
    }

    private void initCanvas() {
        ImageView spriteImageView = model.getSpriteImageView();
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
        model.populateRectanglePropertyLists();
        initAnimationPreview();
    }

    public void initAnimationPreview() {
        gui.getButtonBox().getChildren().remove(model.getPreviewImageView());
        Animation animation = model.getPreviewAnimation(Duration.millis(gui.getDurationSlider().getValue()));
        animation.play();
        gui.getButtonBox().getChildren().add(model.getPreviewImageView());
    }


    private void addFrame() {
        Rectangle clone = cloneRect(model.getRectDrawer());
        model.getRectangleList().add(clone);
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
        model.setSelectedRectangle(r);
        if (model.getRectDrawer() != model.getSelectedRectangle()) {
            removeSelectEffect(model.getRectDrawer());
        }
        Predicate<Rectangle> notSelected = (rect) -> (rect != model.getSelectedRectangle());
        model.getRectangleList().stream().filter(notSelected).forEach(this::removeSelectEffect);
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
        gui.getSpriteGroup().getChildren().remove(model.getRectDrawer());
        model.setRectDrawer(new Rectangle());
        model.getRectDrawer().widthProperty().unbind();
        model.getRectDrawer().heightProperty().unbind();
        model.rectinitXProperty().set(0.0);
        model.rectinitYProperty().set(0.0);
        model.rectXProperty().set(0.0);
        model.rectYProperty().set(0.0);
        model.getRectDrawer().widthProperty().bind(model.rectXProperty().subtract(model.rectinitXProperty()));
        model.getRectDrawer().heightProperty().bind(model.rectYProperty().subtract(model.rectinitYProperty()));
        model.getRectDrawer().setFill(Color.TRANSPARENT);
        model.getRectDrawer().setStroke(Color.BLACK);
        gui.getSpriteScroll().requestFocus(); //ugh someone fix this
        gui.getSpriteScroll().setOnKeyPressed(this::keyPress); //this line keeps fucking up
        makeSelected(model.getRectDrawer());
        gui.getSpriteGroup().getChildren().add(model.getRectDrawer());
        return model.getRectDrawer();
    }

    private void keyPress(KeyEvent event) {
        KeyCode keycode = event.getCode();
        switch (keycode) {
            case ENTER:
                addFrame();
                break;
            default:
                model.handleArrowKey(event);
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
        model.getRectDrawer().widthProperty().unbind();
        model.getRectDrawer().heightProperty().unbind();
        Dragger.makeDraggable(model.getRectDrawer());
        model.getSpriteImageView().setCursor(Cursor.DEFAULT);
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
                model.setRectDrawer(initRectangleDrawer());
                model.getRectDrawer().setX(event.getX());
                model.getRectDrawer().setY(event.getY());
                model.rectinitXProperty().set(event.getX());
                model.rectinitYProperty().set(event.getY());
            } else if (event.getButton() == MouseButton.SECONDARY) {
                model.getRectDrawer().setX(event.getX());
                model.getRectDrawer().setY(event.getY());
            }
        }
    }

    private void mouseDragged(MouseEvent event) {
        model.rectXProperty().set(event.getX());
        model.rectYProperty().set(event.getY());
    }

    private void mouseClicked(MouseEvent event) {
        if (changeColorProperty.get()) {
            model.changeColor(event.getX(), event.getY());
        }
    }

}
