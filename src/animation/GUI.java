package animation;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static animation.DoubleConstants.DURATION_DEFAULT;
import static animation.DoubleConstants.DURATION_MAX;
import static animation.DoubleConstants.DURATION_MIN;
import static animation.StringConstants.ANIMATION_NAME_PROMPT;
import static animation.StringConstants.STYLE_SHEET;

/**
 * The GUI class to hold GUI objects.
 *
 * @author Rhondu Smithwick, Melissa Zhang
 */
class GUI {

    private final SimpleObjectProperty<Boolean> changeColorProperty = new SimpleObjectProperty<>(this, "ChangeColor", false);
    private final BorderPane mainPane = new BorderPane();
    private final VBox animationPropertiesBox = new VBox();
    private final VBox buttonBox = new VBox();
    private final VBox frameButtonBox = new VBox();
    private final VBox leftBox = new VBox();
    private final Group spriteGroup = new Group();
    private final ScrollPane spriteScroll = new ScrollPane(spriteGroup);
    private final Scene scene = new Scene(mainPane, DoubleConstants.WIDTH.get(), DoubleConstants.HEIGHT.get());
    private final Canvas canvas = new Canvas();
    private final TextField animationName = new TextField();
    private final Button activateTransparencyButton = UtilityUtilities.makeButton("Activate Transparency", e -> makeTransparent());
    private Slider durationSlider;
	private Label savedAnimationLabel = new Label("Not Saved");

    /**
     * Sole constructor.
     *
     * @param changeColorProperty the property of whether to change color
     */
    public GUI(SimpleObjectProperty<Boolean> changeColorProperty) {
        scene.getStylesheets().add(STYLE_SHEET.get());
        this.changeColorProperty.bindBidirectional(changeColorProperty);
        leftBox.getChildren().add(animationPropertiesBox);
        leftBox.getChildren().add(new ScrollPane(frameButtonBox));
        mainPane.setCenter(spriteScroll);
        mainPane.setRight(new ScrollPane(buttonBox));
        mainPane.setLeft(leftBox);
    }

    public void initAnimationNameAndDurationFields(SpriteUtility spriteUtility) {
        animationPropertiesBox.getChildren().clear();
        getAnimationName().setText(ANIMATION_NAME_PROMPT.get());
        Label durationTextLabel = new Label("Duration");
        Label durationValueLabel = new Label(String.valueOf(DURATION_DEFAULT.get()));
        durationSlider = UtilityUtilities.makeSlider((ov, old_val, new_val) -> {
            durationValueLabel.setText(String.format("%.2f", new_val.floatValue()));
            if (spriteUtility.hasFrames()) {
                spriteUtility.initAnimationPreview();
            }
        }, DURATION_MIN.get(), DURATION_MAX.get(), DURATION_DEFAULT.get());

        animationPropertiesBox.getChildren().addAll(savedAnimationLabel, getAnimationName(), durationTextLabel, durationSlider,
                durationValueLabel);
    }

    public void addRectangleToDisplay(Rectangle clone, Label label, Button button) {
        spriteGroup.getChildren().add(clone);
        spriteGroup.getChildren().add(label);  
        frameButtonBox.getChildren().add(button);
    }
    public Label getSavedAnimationLabel(){
    	return savedAnimationLabel;
    }

    public VBox displayRectangleListProperties(Rectangle clone) {
        List<String> propertyList = Arrays.asList("x", "y", "width", "height");
        VBox box = new VBox();
        for (String propertyName : propertyList) {
            Label label = new Label(propertyName);
            TextField field = new TextField();
            field.setMinWidth(50);
            box.getChildren().addAll(label, field);
            try {
                Method method = clone.getClass().getMethod(propertyName + "Property");
                DoubleProperty rectProperty = (DoubleProperty) method.invoke(clone);
                StringConverter<Number> converter = new NumberStringConverter();
                Bindings.bindBidirectional(field.textProperty(), rectProperty, converter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return box;
    }

    public void initNewImage(ImageView imageView) {
        spriteGroup.getChildren().add(imageView);
        Bounds bounds = imageView.getBoundsInLocal();
        canvas.setHeight(bounds.getWidth());
        canvas.setWidth(bounds.getHeight());
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

    public void addButton(Button button, VBox box) {
        button.setMaxWidth(Double.MAX_VALUE);
        box.getChildren().add(button);
    }

    public Slider getDurationSlider() {
        return durationSlider;
    }

    public ScrollPane getSpriteScroll() {
        return spriteScroll;
    }

    public Scene getScene() {
        return scene;
    }

    public VBox getButtonBox() {
        return buttonBox;
    }

    public Group getSpriteGroup() {
        return spriteGroup;
    }

    public TextField getAnimationName() {
        return animationName;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public Button getActivateTransparencyButton() {
        return activateTransparencyButton;
    }

	public void updateButtonDisplay(List<Button> buttonList) {
		frameButtonBox.getChildren().clear();
		frameButtonBox.getChildren().addAll(buttonList);
	}
	
}


