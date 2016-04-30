package view.editor.eventeditor;

import model.component.visual.AnimatedSprite;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;


import javafx.scene.control.Alert.AlertType;
import view.editor.AnimationEditor;
import view.utilities.Alerts;
import view.utilities.PopUp;
import api.IEntity;

public class AnimationChooser{
	private static final double WIDTH = 500;
	private static final double HEIGHT = 500;
	private Group sceneGroup = new Group();
	private ScrollPane scrollPane = new ScrollPane(sceneGroup);
	private IEntity myEntity;
	public AnimationChooser(IEntity entity){
		myEntity = entity;
		PopUp popup = new PopUp(WIDTH, HEIGHT);
		popup.show(scrollPane);
		checkIfAnimatedSprite();
		populateLayout();
		
	}
	private void populateLayout() {
		// TODO Auto-generated method stub
		
	}
	private void checkIfAnimatedSprite() {
		if (!myEntity.hasComponent(AnimatedSprite.class)){
			if (Alerts.showAlert("Entity needs an animated sprite", "Add an Animated Sprite","Click OK to add a sprite", AlertType.CONFIRMATION));
				initAnimationEditor();
				
		}
		
		
	}
	private void initAnimationEditor() {
		AnimationEditor animationEditor = new AnimationEditor(myEntity);
		animationEditor.populateLayout();
		PopUp myPopUp = new PopUp(WIDTH,HEIGHT);
		myPopUp.show(animationEditor.getPane());
	}
}
