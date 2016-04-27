package view.editor.entityeditor;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ChoiceDialog;

public abstract class ComponentModifier {
	
	private ResourceBundle myResources;
	
	public ComponentModifier(String language){
		myResources = ResourceBundle.getBundle(language);
	}
	
	protected String makeAndShowChooser(String title, List<String> components){
		ChoiceDialog<String >componentBox = new ChoiceDialog<>(myResources.getString(title), components);
		componentBox.showAndWait();
		String chosen = componentBox.getSelectedItem();
		if(chosen.equals(myResources.getString(title))||chosen==null){
			return null;
		}
		return myResources.getString(chosen);
	}
	
	public abstract void modifyComponentList();

}
