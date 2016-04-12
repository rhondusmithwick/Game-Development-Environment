package view.editor;


import java.util.ResourceBundle;
import api.ISerializable;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import view.Utilities;

public class EditorFactory {

	// language -> used in the resource bundle for all editors
	// toEdit -> entity to be edited for editorEntity; entitySystem to be edited for editorEnvironment
	// masterEntityList -> master list with all current entities, observed 
	//							  -> editorEntity will add to upon saving, it the editor is not already contained in the list
	//							  -> editorEnvironment will use this list to populate its layout
	// entitySystemList -> master list with all current entity systems (i.e. environments), observed
	//							  -> editorEntity will receive an empty one of these, should do nothing to 
	//							  -> editorEnvironment will add to this upon saving, if the system is not already contained in the list
	
	public Editor createEditor(Class<?> name,  String language, ISerializable toEdit, ObservableList<ISerializable> masterEntityList, ObservableList<ISerializable> entitySystemList) {
		Editor editor = null;
		try {
			editor = (Editor) name.getConstructor( String.class, ISerializable.class, ObservableList.class, ObservableList.class).newInstance( language, toEdit, masterEntityList, entitySystemList);
		} catch (Exception e) {
			Utilities.showAlert(ResourceBundle.getBundle(language).getString("error"), null,
					ResourceBundle.getBundle(language).getString("noEditorCreated"), AlertType.ERROR);
		}
		return editor;
	}
}
