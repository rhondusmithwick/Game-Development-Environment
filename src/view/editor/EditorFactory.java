package view.editor;


import java.util.ResourceBundle;

import api.ISerializable;
import javafx.collections.ObservableList;
import view.Utilities;

public class EditorFactory {

	public Editor createEditor(Class<?> name, String language, ObservableList<ISerializable> masterList, ObservableList<ISerializable> otherList) {
		Editor editor = null;
		try {
			editor = (Editor) name.getConstructor( String.class, ObservableList.class, ObservableList.class).newInstance( language, masterList, otherList);
		} catch (Exception e) {
			e.printStackTrace();
			Utilities.showError("editCreate", ResourceBundle.getBundle(language));
		}
		return editor;
	}
}
