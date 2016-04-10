package view.editor;


import java.util.ResourceBundle;
import api.ISerializable;
import javafx.collections.ObservableList;
import view.Utilities;

public class EditorFactory {

	public Editor createEditor(Class<?> name,  String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> otherList) {
		Editor editor = null;
		try {
			editor = (Editor) name.getConstructor( String.class, ISerializable.class, ObservableList.class, ObservableList.class).newInstance( language, toEdit, masterList, otherList);
		} catch (Exception e) {
			Utilities.showError("editCreate", ResourceBundle.getBundle(language));
		}
		return editor;
	}
}
