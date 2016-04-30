package view.editor;

import java.io.File;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import model.component.visual.AnimatedSprite;
import model.entity.Entity;
import view.editor.entityeditor.EditorEntity;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

public class AnimationEditor extends Editor{
	String myLanguage = "";
	private Group sceneGroup = new Group();
	private ScrollPane scrollPane = new ScrollPane(sceneGroup);
	private AnimatedSprite animatedComponent;
	private File spriteProperties;
	private File spriteSheet;
	private Button addAnimationButton;;
	public AnimationEditor(Entity entity){
		animatedComponent = entity.getComponent(AnimatedSprite.class);
		ButtonFactory buttonFactory = new ButtonFactory();
		addAnimationButton = buttonFactory.makeButton("Add SpriteSheet", e-> {getSpriteSheet();
														getPropertiesFile();
			
		});
		
	}
	private void getPropertiesFile() {
		spriteProperties = FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("Properties Files", "*.properties"), "Choose Sprite Properties", null);
		System.out.println(spriteProperties);
	}
	private void getSpriteSheet() {
		spriteSheet = FileUtilities.promptAndGetFile(new FileChooser.ExtensionFilter("All Images", "*.*"), "Choose Sprite Sheet", null);
		System.out.println(spriteSheet);
	}
	@Override
	public ScrollPane getPane() {
		return scrollPane;
	}
	@Override
	public void populateLayout() {
		sceneGroup.getChildren().add(addAnimationButton);
	}
	@Override
	public void updateEditor() {
		// TODO Auto-generated method stub
		
	}
}
