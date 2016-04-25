package animation;

import javafx.animation.Animation;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
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
import animation.Dragger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static animation.SaveHandler.saveAnimations;
import static animation.SaveHandler.saveImage;
import static animation.UtilityUtilities.makeButton;

/**
 * The class for the Animation Utility.
 *
 * @author Melissa Zhang, Rhondu Smithwick, Bruna Liborio
 */
class SpriteUtility {
    private final SimpleObjectProperty<Boolean> changeColorProperty = new SimpleObjectProperty<>(this, "ChangeColor", false);
    private final GUI gui = new GUI(changeColorProperty);
    private final RectangleDrawer rectDrawer = new RectangleDrawer();
    private final RectangleLogic rectangleLogic = new RectangleLogic(rectDrawer);
    private final ImageLogic imageLogic = new ImageLogic();
    private final Map<String, Map<String, String>> animationMap = new HashMap<>();
    private final ResourceBundle utilityResources = ResourceBundle.getBundle("animation.utilityAlerts");
    private Animation previewAnimation;

    public void init(Stage stage) {
        stage.setScene(gui.getScene());
        initializeGui();
    }

    private void initializeGui() {
        rectangleLogic.setSelectedRectangle(null);
        reinitializeGui();
        initButtons();
    }

	private void reinitializeGui() {
        gui.getSpriteGroup().getChildren().clear();
        animationMap.clear();
        initNewImage();
        initCanvas();
        initRectangleDrawer();
        reInitialize();
    }

    private void initButtons() {
        gui.addButton(makeButton(utilityResources.getString("SaveToFile"), e -> saveAnimations(imageLogic.getSpriteSheetPath(), animationMap)), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("NewAnimation"), e -> newAnimation()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("NewSprite"), e -> newSprite()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("PreviewAnimation"), e -> animationPreview()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("SaveAnimation"), e -> saveAnimation()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("AddFrame"), e -> addFrame()), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("DeleteFrame"), e -> deleteFrame(rectangleLogic.getSelectedRectangle())), gui.getButtonBox());
        gui.addButton(gui.getActivateTransparencyButton(), gui.getButtonBox());
        gui.addButton(makeButton(utilityResources.getString("SaveImage"), e -> saveImage(imageLogic.getSpriteImage())), gui.getButtonBox());
    }

	private void newSprite() {
		if(UtilityUtilities.showAlert(utilityResources.getString("NewSpriteTitle"), utilityResources.getString("NewSpriteHeader"), utilityResources.getString("NewSpriteMessage"), AlertType.CONFIRMATION))
		reinitializeGui();
	}

    private void newAnimation() {
    	if(UtilityUtilities.showAlert(utilityResources.getString("NewAlertTitle"), utilityResources.getString("NewAlertHeader"),utilityResources.getString("NewAlertMessage"), AlertType.CONFIRMATION)){
		reInitialize();
    	}
	}

	public void saveAnimation(String name, Map<String, String> animationMap) {
        this.animationMap.put(name, animationMap);
    }

    private void saveAnimation() {
        Double duration = gui.getDurationSlider().getValue();
        Map<String, String> moveAnimationMap = rectangleLogic.getAnimationMap();
        moveAnimationMap.put("duration", String.format("%.2f", duration));
        moveAnimationMap.put("count",rectangleLogic.getAnimationMap().size()+"");
        String name = gui.getAnimationName().getText();
        if (name.contains(" ")){ 
        	UtilityUtilities.showError(utilityResources.getString("SaveErrorTitle"), utilityResources.getString("SaveErrorMessage"));
        }else{
        	if(UtilityUtilities.showAlert(utilityResources.getString("SaveAlertTitle"), utilityResources.getString("SaveAlertHeader"), utilityResources.getString("SaveAlertMessage"), AlertType.CONFIRMATION));{
        		saveAnimation(name, moveAnimationMap);
        	}
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
               // initAnimationProperties();
            }
        }
    }
    
    private void repopulateButtons() {
		rectangleLogic.getButtonList().clear();
		
	}

	private void repopulateLabels() {
		rectangleLogic.getLabelList().clear();
		rectangleLogic.getButtonList().clear();
		for (Rectangle rect : rectangleLogic.getRectangleList()){
			Label label = makeLabel(rect,rectangleLogic.getRectangleList().indexOf(rect)+1);
			rectangleLogic.getLabelList().add(label);
			Button button = UtilityUtilities.makeButton(label.getText(),e -> popUpProperties(Integer.parseInt(label.getText())));
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

    private void animationPreview() {
        initAnimationPreview();
    }

    public void initAnimationPreview() {
        if (previewAnimation != null) {
            previewAnimation.stop();
        }
        gui.getButtonBox().getChildren().remove(imageLogic.getPreviewImageView());
        previewAnimation = getPreviewAnimation();
        previewAnimation.play();
        gui.getButtonBox().getChildren().add(imageLogic.getPreviewImageView());
    }

    public Animation getPreviewAnimation() {
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
        Button button = UtilityUtilities.makeButton("Frame #" + label.getText(),e -> popUpProperties(Integer.parseInt(label.getText())));
        rectangleLogic.getButtonList().add(button);

        gui.addRectangleToDisplay(clone,label, button);
    }

    @SuppressWarnings("rawtypes")
	private Object popUpProperties(int id) {
		Dialog dialog = UtilityUtilities.popUp("Frame Properties","Edit Frame Properties.");
		VBox box = gui.displayRectangleListProperties(rectangleLogic.getRectangleList().get(id-1));
		dialog.getDialogPane().setContent(box);
		dialog.showAndWait();
		return null;
	}

	private Label makeLabel(Rectangle clone, int frameID){
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
        gui.getSpriteScroll().requestFocus(); //ugh someone fix this
        gui.getSpriteScroll().setOnKeyPressed(this::keyPress); //this line keeps fucking up
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
