package view.editor.eventeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import model.component.visual.AnimatedSprite;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import view.editor.AnimationEditor;
import view.utilities.Alerts;
import view.utilities.PopUp;
import api.IEntity;

public class AnimationChooser{
	private static final double WIDTH = 400;
	private static final double HEIGHT = 400;
	private IEntity myEntity;
	private AnimatedSprite animatedSprite;
	private ChoiceDialog<String> dialog = new ChoiceDialog<String>();
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
			updateDialogBox(animationNames);
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				return result.get();
			}
		}else{
			if (Alerts.showAlert(myResources.getString("noAnimatedSpriteTitle"), myResources.getString("noAnimatedSpriteHeader"),myResources.getString("noAnimatedSpriteMessage"), AlertType.CONFIRMATION)){
				initAnimationEditor();



			}
		
		}




		return null;
	}
	
	private void updateDialogBox(List<String>choices) {
		dialog = new ChoiceDialog<>(choices.get(0), choices);
		dialog.setTitle("Animation Chooser");
		dialog.setHeaderText("Choose an animation");
		dialog.setContentText("Choose");
		
	
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
