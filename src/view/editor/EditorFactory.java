package view.editor;

import model.entity.Entity;
import model.entity.EntitySystem;
import api.IEntity;
import api.IEntitySystem;
import api.ISerializable;

public class EditorFactory {

	public Editor createEditor(String language, Class<?> name) {
		Editor editor = null;
		try {
			editor = (Editor) name.getConstructor(String.class, ISerializable.class).newInstance(language, ISerializable.class);
		} catch (Exception e) {
			System.out.println("EDITOR FACTORY FAILED TO CREATE CLASS");
		}
		return editor;
	}
}
