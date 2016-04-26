package animation.main;

import animation.gui.Dragger;
import animation.gui.GUI;
import animation.model.ImageLogic;
import animation.model.RectangleDrawer;
import animation.model.RectangleLogic;
import animation.utility.GUIUtilities;
import javafx.animation.Animation;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static animation.utility.GUIUtilities.makeButton;
import static animation.utility.GUIUtilities.showAlert;
import static animation.utility.GUIUtilities.showError;
import static animation.utility.SaveHandler.saveAnimations;
import static animation.utility.SaveHandler.saveImage;

/**
 * The class for the Animation Utility.
 *
 * @author Melissa Zhang, Rhondu Smithwick, Bruna Liborio
 */
public class SpriteUtility {
    private final SimpleObjectProperty<Boolean> changeColorProperty = new SimpleObjectProperty<>(this, "ChangeColor", false);
    private final BooleanProperty savedAnimationProperty = new SimpleBooleanProperty(false);
    private final GUI gui = new GUI(changeColorProperty);
    private final RectangleDrawer rectDrawer = new RectangleDrawer();
    private final RectangleLogic rectangleLogic = new RectangleLogic(rectDrawer);
    private final ImageLogic imageLogic = new ImageLogic();
    private final Map<String, Map<String, String>> animationMap = new HashMap<>();
    private final ResourceBundle utilityResources = ResourceBundle.getBundle("animation/config/utilityAlerts");
    private Animation previewAnimation;

    /**
     * Initialize with provided stage.
     *
     * @param stage the stage
     */
    public void init(Stage stage) {
        stage.setScene(gui.getScene());
        initializeGui();
    }

    /**
     * Save the animation.
     *
     * @param name         the name of the animation
     * @param animationMap the animation Map
     */
    private void saveAnimation(String name, Map<String, String> animationMap) {
        this.animationMap.put(name, animationMap);
    }

    private void initializeGui() {
        rectangleLogic.setSelectedRectangle(null);
        reinitializeGui();
        initButtons();
    }

    private void reinitializeGui() {
        gui.getSpriteGroup().getChildren().clear();
        animationMap.clear();
        initSavedAnimationLabel();
        initNewImage();
        initCanvas();
        initRectangleDrawer();
        reInitialize();
    }

    private void initSavedAnimationLabel() {
        savedAnimationProperty.addListener((ov, oldVal, newVal) -> {
            if (newVal) {
                gui.getSavedAnimationLabel().setText(gui.getAnimationName().getText() + " Saved");
            } else {
                gui.getSavedAnimationLabel().setText(gui.getAnimationName().getText() + " Not Saved");
            }
        });
    }

    private void initButtons() {
        gui.addButton(makeButton(utilityResources.getString("SaveToFile"), e -> saveAnimations(imageLogic.getSpriteSheetPath(), animationMap)), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("NewAnimation"), e -> newAnimation()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("NewSprite"), e -> newSprite()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("PreviewAnimation"), e -> initAnimationPreview()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("SaveAnimation"), e -> saveAnimation()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("AddFrame"), e -> addFrame()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("DeleteFrame"), e -> deleteFrame(rectangleLogic.getSelectedRectangle())), gui.getButtonBox());
        gui.addButton(gui.getActivateTransparencyButton(), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("SaveImage"), e -> saveImage(imageLogic.getSpriteImage())), gui.getButtonBox());
    }

    private void newSprite() {
        boolean shouldReinitialize = showAlert(utilityResources.getString("NewSpriteTitle"),
                utilityResources.getString("NewSpriteHeader"),
                utilityResources.getString("NewSpriteMessage"), AlertType.CONFIRMATION);
        if (shouldReinitialize) {
            reinitializeGui();
        }
    }

    private void newAnimation() {
        if (!savedAnimationProperty.get()) {
            if (!showAlert(utilityResources.getString("NewAlertTitle"), utilityResources.getString("NewAlertHeader"), utilityResources.getString("NewAlertMessage"), AlertType.CONFIRMATION)) {
                System.out.println("hi");
                return;
            }
        }
        savedAnimationProperty.set(false);
        reInitialize();
    }


    private void saveAnimation() {
        Double duration = gui.getDurationSlider().getValue();
        Map<String, String> moveAnimationMap = rectangleLogic.getAnimationMap();
        moveAnimationMap.put("duration", String.format("%.2f", duration));
        String name = gui.getAnimationName().getText();
        if (name.contains(" ")) {
            showError(utilityResources.getString("SaveErrorTitle"), utilityResources.getString("SaveErrorMessage"));
        } else {
            savedAnimationProperty.set(true);
            saveAnimation(name, moveAnimationMap);

        }
    }

    private void deleteFrame(Rectangle frameToDelete) {
        if (frameToDelete != null) {
            boolean removed = rectangleLogic.removeRectangle(frameToDelete);
            if (removed) {
                gui.getSpriteGroup().getChildren().remove(frameToDelete);
                gui.getSpriteGroup().getChildren().removeAll(rectangleLogic.getLabelList());
                repopulateButtons();
                repopulateLabels();
                savedAnimationProperty.set(false);

                // initAnimationProperties();
            }
        }
    }

    private void repopulateButtons() {
        rectangleLogic.getButtonList().clear();
        // TODO: ??
    }

    private void repopulateLabels() {
        gui.getSpriteGroup().getChildren().removeAll(rectangleLogic.getLabelList());
        rectangleLogic.getLabelList().clear();
        rectangleLogic.getButtonList().clear();
        for (Rectangle rect : rectangleLogic.getRectangleList()) {
            Label label = makeLabel(rect, rectangleLogic.getRectangleList().indexOf(rect) + 1);
            rectangleLogic.getLabelList().add(label);
            Button button = makeButton("Frame #" + label.getText(), e -> popUpProperties(Integer.parseInt(label.getText())));
            rectangleLogic.getButtonList().add(button);
        }
        gui.updateButtonDisplay(rectangleLogic.getButtonList());
        gui.getSpriteGroup().getChildren().addAll(rectangleLogic.getLabelList());
    }

    private void initAnimationProperties() {
        gui.getButtonBox().getChildren().remove(imageLogic.getPreviewImageView());
        gui.initAnimationNameAndDurationFields(this);
        //rectangleLogic.getRectangleList().stream().forEach(gui::displayRectangleListProperties);
    }


    private void reInitialize() {
        gui.getSpriteGroup().getChildren().removeAll(rectangleLogic.getRectangleList());
        rectangleLogic.getRectangleList().clear();
        initAnimationProperties();
        repopulateLabels();
    }

    private void initNewImage() {
        imageLogic.initNewImage();
        gui.initNewImage(imageLogic.getSpriteImageView());
    }

    private void initCanvas() {
        ImageView spriteImageView = imageLogic.getSpriteImageView();
        gui.getCanvas().layoutXProperty().bind(spriteImageView.layoutXProperty());
        gui.getCanvas().layoutYProperty().bind(spriteImageView.layoutYProperty());
        initCanvasHandlers(gui.getCanvas());
        gui.getSpriteGroup().getChildren().add(gui.getCanvas());
    }

    private void initCanvasHandlers(Canvas canvas2) {
        canvas2.setOnMouseDragged(rectDrawer::handleMouseDragged);
        canvas2.setOnMousePressed(this::handleMousePressed);
        canvas2.setOnMouseReleased(this::mouseReleased);
        canvas2.setOnMouseClicked(this::handleMouseClicked);
    }

    /**
     * Create the preview animation.
     */
    public void initAnimationPreview() {
        if (previewAnimation != null) {
            previewAnimation.stop();
        }
        gui.getButtonBox().getChildren().remove(imageLogic.getPreviewImageView());
        previewAnimation = getPreviewAnimation();
        previewAnimation.play();
        gui.getButtonBox().getChildren().add(imageLogic.getPreviewImageView());
    }

    /**
     * Get the preview Animation.
     *
     * @return the preview animation
     */
    private Animation getPreviewAnimation() {
        ImageView previewImageView = imageLogic.getPreviewImageView();
        previewImageView.setImage(imageLogic.getSpriteImage());
        Duration duration = Duration.millis(gui.getDurationSlider().getValue());
        Animation animation = rectangleLogic.getAnimation(previewImageView, duration);
        animation.setCycleCount(Animation.INDEFINITE);
        return animation;
    }

    private void addFrame() {
        Rectangle clone = rectangleLogic.cloneRect(rectDrawer.getRectangle());
        rectangleLogic.getRectangleList().add(clone);
        Label label = makeLabel(clone, rectangleLogic.getRectangleList().size());
        rectangleLogic.getLabelList().add(label);
        Button button = GUIUtilities.makeButton("Frame #" + label.getText(), e -> popUpProperties(Integer.parseInt(label.getText())));
        rectangleLogic.getButtonList().add(button);
        savedAnimationProperty.set(false);
        gui.addRectangleToDisplay(clone, label, button);
    }

    @SuppressWarnings("rawtypes")
    private Object popUpProperties(int id) {
        Dialog dialog = GUIUtilities.popUp(utilityResources.getString("EditFrameTitle"), utilityResources.getString("EditFrameMessage") + "Frame " + id);
        VBox box = gui.displayRectangleListProperties(rectangleLogic.getRectangleList().get(id - 1));
        dialog.getDialogPane().setContent(box);
        dialog.showAndWait();
        savedAnimationProperty.set(false);
        return null; // TODO: ??
    }

    private Label makeLabel(Rectangle clone, int frameID) {
        Label label = new Label(String.valueOf(frameID));
        label.setFont(new Font("Arial", 30));
        label.setTextFill(Color.RED);
        label.layoutXProperty().bind(clone.xProperty());
        label.layoutYProperty().bind(clone.yProperty());
        return label;
    }

    private void initRectangleDrawer() {
        gui.getSpriteGroup().getChildren().remove(rectDrawer.getRectangle());
        rectDrawer.reset();
        gui.getSpriteScroll().requestFocus();
        gui.getSpriteScroll().setOnKeyPressed(this::keyPress);
        rectangleLogic.makeSelected(rectDrawer.getRectangle());
        gui.getSpriteGroup().getChildren().add(rectDrawer.getRectangle());
    }

    private void keyPress(KeyEvent event) {
        KeyCode keycode = event.getCode();
        switch (keycode) {
            case ENTER:
                addFrame();
                break;
            default:
                rectangleLogic.handleKeyInput(event);
        }
        event.consume();
    }


    private void mouseReleased(MouseEvent event) {
        rectDrawer.getRectangle().widthProperty().unbind();
        rectDrawer.getRectangle().heightProperty().unbind();
        Dragger.makeDraggable(rectDrawer.getRectangle());
        imageLogic.getSpriteImageView().setCursor(Cursor.DEFAULT);
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
            rectDrawer.handleMousePressed(event);
        }
    }

    private void handleMouseClicked(MouseEvent event) {
        if (changeColorProperty.get()) {
            imageLogic.changeColor(event.getX(), event.getY());
        }
    }

    public boolean hasFrames() {
        return !rectangleLogic.getRectangleList().isEmpty();
    }
}
