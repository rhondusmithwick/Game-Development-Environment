package animation;

import javafx.animation.Animation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.Dragger;

import java.io.File;

import static animation.SaveHandler.saveAnimations;
import static animation.SaveHandler.saveImage;
import static animation.StringConstants.ADD_FRAME;
import static animation.StringConstants.DELETE_FRAME;
import static animation.StringConstants.NEW_ANIMATION;
import static animation.StringConstants.NEW_SPRITE;
import static animation.StringConstants.PREVIEW_ANIMATION;
import static animation.StringConstants.SAVE_ANIMATION;
import static animation.StringConstants.SAVE_ANIMATIONS_TO_FILE;
import static animation.UtilityUtilities.makeButton;

/**
 * The class for the Animation Utility.
 *
 * @author Melissa Zhang, Rhondu Smithwick, Bruna Liborio
 */
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
        gui.addButton(makeButton(SAVE_ANIMATIONS_TO_FILE.get(), e -> saveAnimations(spriteSheetPath, model.getAnimationList())), gui.getButtonBox());
        gui.addButton(makeButton(NEW_ANIMATION.get(), e -> reInitialize()), gui.getButtonBox());
        gui.addButton(makeButton(NEW_SPRITE.get(), e -> reinitializeGui()), gui.getButtonBox());
        gui.addButton(makeButton(PREVIEW_ANIMATION.get(), e -> animationPreview()), gui.getButtonBox());
        gui.addButton(makeButton(SAVE_ANIMATION.get(), e -> model.saveAnimation(gui.getAnimationName().getText(), gui.getDurationSlider().getValue())), gui.getButtonBox());
        gui.addButton(makeButton(ADD_FRAME.get(), e -> addFrame()), gui.getButtonBox());
        gui.addButton(makeButton(DELETE_FRAME.get(), e -> deleteFrame(model.getSelectedRectangle())), gui.getButtonBox());
        gui.addButton(gui.getActivateTransparencyButton(), gui.getButtonBox());
        gui.addButton(makeButton("Save Image", e -> saveImage(model.getSpriteImage())), gui.getButtonBox());
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

    private void initAnimationProperties() {
        gui.getButtonBox().getChildren().remove(model.getPreviewImageView());
        gui.initAnimationNameAndDurationFields(this);
        model.getRectangleList().stream().forEach(gui::displayRectangleListProperties);
    }


    private void reInitialize() {
        gui.getSpriteGroup().getChildren().removeAll(model.getRectangleList());
        model.getRectangleList().clear();
        initAnimationProperties();
    }

    private void initNewImage() {
        model.setSpriteImage(initFileChooser());
        gui.initNewImage(model.getSpriteImageView());
    }

    private void initCanvas() {
        ImageView spriteImageView = model.getSpriteImageView();
        gui.getCanvas().layoutXProperty().bind(spriteImageView.layoutXProperty());
        gui.getCanvas().layoutYProperty().bind(spriteImageView.layoutYProperty());
        initCanvasHandlers(gui.getCanvas());
        gui.getSpriteGroup().getChildren().add(gui.getCanvas());
    }

    private void initCanvasHandlers(Canvas canvas2) {
        canvas2.setOnMouseDragged(model::handleMouseDragged);
        canvas2.setOnMousePressed(this::handleMousePressed);
        canvas2.setOnMouseReleased(this::mouseReleased);
        canvas2.setOnMouseClicked(this::handleMouseClicked);
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
        Rectangle clone = model.cloneRect(model.getRectDrawer());
        model.getRectangleList().add(clone);
        gui.addRectangleToDisplay(clone);
    }

    /*
   * Initializes initial rectangle drawer
   */
    private void initRectangleDrawer() {
        gui.getSpriteGroup().getChildren().remove(model.getRectDrawer());
        model.resetRectangleDrawer();
        gui.getSpriteScroll().requestFocus(); //ugh someone fix this
        gui.getSpriteScroll().setOnKeyPressed(this::keyPress); //this line keeps fucking up
        model.makeSelected(model.getRectDrawer());
        gui.getSpriteGroup().getChildren().add(model.getRectDrawer());
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

    private void handleMousePressed(MouseEvent event) {
        if (!changeColorProperty.get()) {
            gui.getCanvas().setCursor(Cursor.CLOSED_HAND);
            if (event.getButton() == MouseButton.PRIMARY) {
                initRectangleDrawer();
            }
            model.handleMousePressed(event);
        }
    }


    private void handleMouseClicked(MouseEvent event) {
        if (changeColorProperty.get()) {
            model.changeColor(event.getX(), event.getY());
        }
    }
}
