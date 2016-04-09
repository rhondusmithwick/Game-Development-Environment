package view;

import model.entity.Entity;
import model.entity.EntitySystem;
import api.IEntity;
import api.IEntitySystem;

public class EditorFactory {
	
	public Editor createEditor(Class<?> name) {
		Editor editor = null;
		try {
			if(name.equals(EditorEnvironment.class)) {
				editor = (Editor) name.getConstructor(IEntitySystem.class).newInstance(new EntitySystem());
			} else {
				editor = (Editor) name.getConstructor(IEntity.class).newInstance(new Entity());
			}
		} catch (Exception e) {
				System.out.println("EDITOR FACTORY FAILED TO CREATE CLASS");
		}
		return editor;
	}
}
