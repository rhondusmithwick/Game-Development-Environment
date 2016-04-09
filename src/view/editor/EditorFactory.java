package view.editor;

import com.google.common.reflect.Reflection;

import api.ISerializable;

public class EditorFactory {
	
	public Editor createEditor(String name, String language, ISerializable object) {
		Editor editor = null;
		String pack = Reflection.getPackageName(this.getClass());
		try {
				editor = (Editor) Class.forName(pack + "." + name).getConstructor(String.class,ISerializable.class).newInstance(language,object);
		} catch (Exception e) {
				System.out.println("EDITOR FACTORY FAILED TO CREATE CLASS");
		}
		return editor;
	}
}
