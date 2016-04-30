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
import utility.FilePathRelativizer;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;
import view.utilities.TextFieldFactory;

public class AnimationEditor extends Editor{
	private static final String SPRITE_PROPERTIES = "spriteProperties.";
	private static final String DIR = "resources/";
	private Group sceneGroup = new Group();
	private ScrollPane scrollPane = new ScrollPane(sceneGroup);
	private VBox vbox = new VBox();
	private AnimatedSprite animatedSpriteComponent;
	private String spriteProperties;
	private String spriteSheet;
	private Button spriteSheetButton;
	private TextField spriteSheetField;
	private TextField spritePropertiesField;
	private Button spritePropertiesButton;
	private Button saveButton;
	private Label errorLabel;
	public AnimationEditor(IEntity entity){
		sceneGroup.getChildren().add(vbox);
		if(entity.hasComponent(AnimatedSprite.class)){
			animatedSpriteComponent = entity.getComponent(AnimatedSprite.class);
		}else{
			animatedSpriteComponent = new AnimatedSprite();
			entity.addComponent(animatedSpriteComponent);
		}
		

		
	}
	private void getPropertiesFile() {
		spriteProperties = FilePathRelativizer.convertToResourceBase(FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("Properties Files", "*.properties"), "Choose Sprite Properties", DIR).getPath(), SPRITE_PROPERTIES);
		animatedSpriteComponent.setBundlePath(spriteProperties);
		spritePropertiesField.setText(spriteProperties);
	}
	private void getSpriteSheet() {
		spriteSheet = FilePathRelativizer.relativize(FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"), "Choose Sprite Sheet", DIR).getPath());
		spriteSheetField.setText(spriteSheet);
	}
	@Override
	public ScrollPane getPane() {
		return scrollPane;
	}
	@Override
	public void populateLayout() {
		spriteSheetField = TextFieldFactory.makeTextArea("Sprite Sheet Path");
		spriteSheetField.setEditable(false);
		spritePropertiesField = TextFieldFactory.makeTextArea("Properties Path");
		spritePropertiesField.setEditable(false);
		spriteSheetButton = ButtonFactory.makeButton("Add SpriteSheet", e-> getSpriteSheet());
		spritePropertiesButton = ButtonFactory.makeButton("Add Properties", e->getPropertiesFile());
		errorLabel = new Label();
		saveButton = ButtonFactory.makeButton("Save Animated Sprite", e-> saved());
		vbox.getChildren().addAll(spriteSheetField, spriteSheetButton, spritePropertiesField, spritePropertiesButton, saveButton, errorLabel);
	}
	private void saved() {
		if(!checkIfError()){
			sceneGroup.getChildren().clear();
			Text savedText = new Text("Your AnimatedSprite has been saved! Close this window and continue editing");
			sceneGroup.getChildren().add(savedText);
		}
	}
	private boolean checkIfError() {
		if (spriteSheetField.getText().isEmpty()|| spritePropertiesField.getText().isEmpty()){
			errorLabel.setText("Please choose a sprite sheet and a properties file");
			return true;
			
		}
		return false;
	}
	@Override
	public void updateEditor() {
		// TODO Auto-generated method stub
		
	}
}
