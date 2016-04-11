package view.editor;


import java.util.ResourceBundle;
import api.ISerializable;
import enums.DefaultStrings;
import javafx.collections.ObservableList;
import view.Utilities;

public class EditorFactory {

	public Editor createEditor(Class<?> name,  String language, ISerializable toEdit, ObservableList<ISerializable> masterList, ObservableList<ISerializable> otherList) {
		Editor editor = null;
		try {
			editor = (Editor) name.getConstructor( String.class, ISerializable.class, ObservableList.class, ObservableList.class).newInstance( language, toEdit, masterList, otherList);
		} catch (Exception e) {
			
			Utilities.showError(ResourceBundle.getBundle(language).getString(DefaultStrings.ERROR.getDefault()),
					ResourceBundle.getBundle(language).getString(DefaultStrings.EDITOR_FACTORY_ERROR.getDefault()));
		}
		return editor;
	}
}
