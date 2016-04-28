package view.editor;

import api.IEditor;
import voogasalad.util.reflection.Reflection;

/**
 * Generates a specific type of Editor class.
 *
 * @author Ben Zhang, Cali Nelson
 */

public class EditorFactory {

	public IEditor createEditor(String name, Object... args) {
		return (IEditor) Reflection.createInstance(name, args);
	}
}
