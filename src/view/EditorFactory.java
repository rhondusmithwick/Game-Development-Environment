package view;

import model.entity.Entity;
import model.entity.EntitySystem;
import com.google.common.reflect.Reflection;
import enums.DefaultStrings;

public class EditorFactory {
	
	public Editor createEditor(String name) {
		Editor editor = null;
		String pack = Reflection.getPackageName(this.getClass());
		try {
			if(name.equals(DefaultStrings.ENVIRONMENT_EDITOR_NAME)) {
				editor = (Editor) Class.forName(pack + "." + name).getConstructor(EntitySystem.class).newInstance(new EntitySystem());
			} else {
				editor = (Editor) Class.forName(pack + "." + name).getConstructor(Entity.class).newInstance(new Entity(0));
			}
		} catch (Exception e) {
				System.out.println("EDITOR FACTORY FAILED TO CREATE CLASS");
		}
		return editor;
	}
}
