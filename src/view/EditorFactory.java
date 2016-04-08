package view;

import model.entity.Entity;

import com.google.common.reflect.Reflection;


public class EditorFactory {
	
	public Editor createEditor(String name) {
		Editor editor = null;
		String pack = Reflection.getPackageName(this.getClass());
		try {
			editor = (Editor) Class.forName(pack + "." + name).getConstructor(Entity.class).newInstance(new Entity(0));
		} catch (Exception e) {
				System.out.println("EDITOR FACTORY FAILED TO CREATE CLASS");
		}
		return editor;
	}
}
