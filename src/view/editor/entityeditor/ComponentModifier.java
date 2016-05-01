package view.editor.entityeditor;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ChoiceDialog;
import view.enums.DefaultStrings;

/**
 * 
 * @author calinelson
 *
 */
public abstract class ComponentModifier {
	
	private ResourceBundle myResources, myComponentNames;
	
	public ComponentModifier(String language){
		myResources = ResourceBundle.getBundle(language);
		myComponentNames = ResourceBundle.getBundle(language + DefaultStrings.COMPONENTS.getDefault());
	}
	
	protected String makeAndShowChooser(String title, List<String> components){
		ChoiceDialog<String >componentBox = new ChoiceDialog<>(myResources.getString(title), components);
		componentBox.showAndWait();
		String chosen = componentBox.getSelectedItem();
		if(chosen.equals(myResources.getString(title))||chosen==null){
			return null;
		}
		return myComponentNames.getString(chosen);
	}
	
	public abstract void modifyComponentList();

}
