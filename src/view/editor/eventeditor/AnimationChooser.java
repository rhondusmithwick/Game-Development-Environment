package view.editor.eventeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import model.component.visual.AnimatedSprite;
import javafx.scene.control.Alert.AlertType;
import view.editor.AnimationEditor;
import view.utilities.Alerts;
import view.utilities.ChoiceDialogFactory;
import view.utilities.PopUp;
import api.IEntity;

public class AnimationChooser{
	private static final double WIDTH = 400;
	private static final double HEIGHT = 400;
	private IEntity myEntity;
	private AnimatedSprite animatedSprite;
	private static final String DEFAULT_LANGUAGE = "languages.english";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_LANGUAGE);
	

	public AnimationChooser(IEntity entity){
		myEntity = entity;
	}

	public void setLanguage(String language){
		myResources = ResourceBundle.getBundle(DEFAULT_LANGUAGE);
	}
	private AnimatedSprite getAnimatedSpriteComponent() {
		return myEntity.getComponent(AnimatedSprite.class);
	}

	public String initChooser() {	
		if (checkIfAnimatedSprite()){
			animatedSprite = getAnimatedSpriteComponent();
			List<String> animationNames = new ArrayList<String>(animatedSprite.getAnimationNames());
			return ChoiceDialogFactory.choiceBox(animationNames, myResources.getString("animationChooserTitle"), myResources.getString("animationChooserHeader"), myResources.getString("animationChooserContent"));
			
		}else{
			if (Alerts.showAlert(myResources.getString("noAnimatedSpriteTitle"), myResources.getString("noAnimatedSpriteHeader"),myResources.getString("noAnimatedSpriteMessage"), AlertType.CONFIRMATION)){
				initAnimationEditor();


			}
		
		}

		return null;
	}
	

	private boolean checkIfAnimatedSprite() {
		if (!myEntity.hasComponent(AnimatedSprite.class)){
			return false;

				
		}return true;
		
		
	}
	private void initAnimationEditor() {
		AnimationEditor animationEditor = new AnimationEditor(myEntity, DEFAULT_LANGUAGE);
		animationEditor.populateLayout();
		PopUp myPopUp = new PopUp(WIDTH,HEIGHT);
		myPopUp.show(animationEditor.getPane());
	}
}
