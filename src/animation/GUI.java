package animation;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import static animation.DoubleConstants.DURATION_DEFAULT;
import static animation.DoubleConstants.DURATION_MAX;
import static animation.DoubleConstants.DURATION_MIN;
import static animation.StringConstants.ANIMATION_NAME_PROMPT;

/**
 * Created by rhondusmithwick on 4/23/16.
 *
 * @author Rhondu Smithwick
 */
public class GUI {
    private final SimpleObjectProperty<Boolean> changeColorProperty = new SimpleObjectProperty<>(this, "ChangeColor", false);
    private final BorderPane mainPane = new BorderPane();
    private final VBox animationPropertiesBox = new VBox();
    private final VBox buttonBox = new VBox();
    private final Group spriteGroup = new Group();
    private final ScrollPane spriteScroll = new ScrollPane(spriteGroup);
    private final Scene scene = new Scene(mainPane, DoubleConstants.WIDTH.get(), DoubleConstants.HEIGHT.get());
    private final Canvas canvas = new Canvas();
    private final TextField animationName = new TextField();
    private final Button activateTransparencyButton = UtilityUtilities.makeButton("Activate Transparency", e -> makeTransparent());

    private Slider durationSlider;

    public GUI(SimpleObjectProperty<Boolean> changeColorProperty) {
        this.changeColorProperty.bindBidirectional(changeColorProperty);
    }

    public void init() {
        mainPane.setCenter(spriteScroll);
        mainPane.setRight(new ScrollPane(buttonBox));
        mainPane.setLeft(new ScrollPane(animationPropertiesBox));
    }

    public Slider getDurationSlider() {
        return durationSlider;
    }

    /*
            * Initialize sprite sheet gui properties like animation name and duration
        */
    public void initAnimationNameAndDurationFields(SpriteUtility spriteUtility) {
        getAnimationName().setText(ANIMATION_NAME_PROMPT.get());
        Label durationTextLabel = new Label("Duration");
        Label durationValueLabel = new Label("0.0");
        durationSlider = UtilityUtilities.makeSlider((ov, old_val, new_val) -> {
            durationValueLabel.setText(String.format("%.2f", new_val.floatValue()));
            spriteUtility.initAnimationPreview();
        }, DURATION_MIN.get(), DURATION_MAX.get(), DURATION_DEFAULT.get());
        getAnimationPropertiesBox().getChildren().addAll(getAnimationName(), durationTextLabel, durationSlider,
                durationValueLabel);
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

    public ScrollPane getSpriteScroll() {
        return spriteScroll;
    }

    public Boolean getChangeColorProperty() {
        return changeColorProperty.get();
    }

    public SimpleObjectProperty<Boolean> changeColorPropertyProperty() {
        return changeColorProperty;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public VBox getAnimationPropertiesBox() {
        return animationPropertiesBox;
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
}


