package view.editor;

import java.util.ResourceBundle;
import api.ISerializable;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import view.Utilities;

/**
 * Generates a specific type of Editor class.
 *
 * @author Ben Zhang
 */

public class EditorFactory {

	/**
     * Creates an Editor object from a list of parameters using reflection.
     *
     * @param language used in the resource bundle for all editors
     * @param toEdit entity to be edited for editorEntity; entitySystem to be edited for editorEnvironment
	 * @param masterEntityList - master list with all current entities, observed <br/>
	 *						   - editorEntity will add to upon saving, it the editor is not already contained in the list <br/>
	 *						   - editorEnvironment will use this list to populate its layout
	 * @param entitySystemList - master list with all current entity systems (i.e. environments), observed <br/>
	 *						   - editorEntity will receive an empty one of these, should do nothing to <br/>
	 *						   - editorEnvironment will add to this upon saving, if the system is not already contained in the list
	 * @return the created Editor
	 */
	public Editor createEditor(Class<?> name, String language, ISerializable toEdit, ObservableList<ISerializable> masterEntityList, ObservableList<ISerializable> entitySystemList) {
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
