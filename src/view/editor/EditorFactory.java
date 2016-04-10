package view.editor;


import java.util.ResourceBundle;

import api.ISerializable;
import javafx.scene.control.Button;
import view.Utilities;

public class EditorFactory {

	public Editor createEditor(Class<?> name, ISerializable entity, String language, Button button) {
		Editor editor = null;
		try {
			editor = (Editor) name.getConstructor(ISerializable.class, String.class, Button.class).newInstance(entity, language, button);
		} catch (Exception e) {
			Utilities.showError("editFactory", ResourceBundle.getBundle(language));
		}
		return editor;
	}
}
