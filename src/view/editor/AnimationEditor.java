package view.editor;

import api.IEntity;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.component.visual.AnimatedSprite;
import utility.FilePathUtility;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;
import view.utilities.TextFieldFactory;

import java.util.ResourceBundle;

/**
 * This class is for choosing and editing the AnimatedSprite
 *
 * @author Melissa Zhang
 */
public class AnimationEditor extends Editor {
    private static final String SPRITE_PROPERTIES = "spriteProperties.";
    private static final String DIR = "resources/";
    private final Group sceneGroup = new Group();
    private final ScrollPane scrollPane = new ScrollPane(sceneGroup);
    private final VBox vbox = new VBox();
    private AnimatedSprite animatedSpriteComponent;
    private TextField spriteSheetField;
    private TextField spritePropertiesField;
    private Label errorLabel;
    private ResourceBundle myResources;

    public AnimationEditor (IEntity entity, String language) {
        myResources = ResourceBundle.getBundle(language);

        sceneGroup.getChildren().add(vbox);
        if (entity.hasComponent(AnimatedSprite.class)) {
            animatedSpriteComponent = entity.getComponent(AnimatedSprite.class);
        } else {
            animatedSpriteComponent = new AnimatedSprite();
            entity.forceAddComponent(animatedSpriteComponent, true);
        }


    }

    private void getPropertiesFile () {
        String spriteProperties = FilePathUtility.convertToResourceBase(FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("Properties Files", "*.properties"), "Choose Sprite Properties", DIR).getPath(), SPRITE_PROPERTIES);
        spritePropertiesField.setText(spriteProperties);
        animatedSpriteComponent.setBundlePath(spriteProperties);
    }

    private void getSpriteSheet () {
        String spriteSheet = FilePathUtility.relativize(FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"), "Choose Sprite Sheet", DIR).getPath());
        spriteSheetField.setText(spriteSheet);
        animatedSpriteComponent.setImagePath(spriteSheet);
    }

    @Override
    public ScrollPane getPane () {
        return scrollPane;
    }

    @Override
    public void populateLayout () {
        spriteSheetField = TextFieldFactory.makeTextArea(myResources.getString("spriteSheetPath"));
        spriteSheetField.setEditable(false);
        spritePropertiesField = TextFieldFactory.makeTextArea(myResources.getString("spritePropertiesPath"));
        spritePropertiesField.setEditable(false);
        Button spriteSheetButton = ButtonFactory.makeButton(myResources.getString("addSpriteSheet"), e -> getSpriteSheet());
        Button spritePropertiesButton = ButtonFactory.makeButton(myResources.getString("addProperties"), e -> getPropertiesFile());
        errorLabel = new Label();
        Button saveButton = ButtonFactory.makeButton(myResources.getString("saveAnimatedSprite"), e -> saved());
        vbox.getChildren().addAll(spriteSheetField, spriteSheetButton, spritePropertiesField, spritePropertiesButton, saveButton, errorLabel);
    }

    private void saved () {
        if (!checkIfError()) {
            sceneGroup.getChildren().clear();
            Text savedText = new Text(myResources.getString("saveAnimatedSpriteSuccess"));
            sceneGroup.getChildren().add(savedText);
        }
    }

    private boolean checkIfError () {
        if (spriteSheetField.getText().isEmpty() || spritePropertiesField.getText().isEmpty()) {
            errorLabel.setText(myResources.getString("saveAnimatedSpriteError"));
            return true;

        }
        return false;
    }

    @Override
    public void updateEditor () {
        // TODO Auto-generated method stub

    }
}
