package view.editor;


import api.ISerializable;
import javafx.scene.control.Button;

public class EditorFactory {

	public Editor createEditor(Class<?> name, ISerializable entity, String language, Button button) {
		Editor editor = null;
		try {
			editor = (Editor) name.getConstructor(ISerializable.class, String.class, Button.class).newInstance(entity, language, button);
		} catch (Exception e) {
			System.out.println("EDITOR FACTORY FAILED TO CREATE CLASS");
		}
		return editor;
	}
}
