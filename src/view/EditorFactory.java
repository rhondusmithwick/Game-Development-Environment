package view;

import model.entity.Entity;
import com.google.common.reflect.Reflection;

public class EditorFactory {
	
	public Editor createEditor(String name) {
		Editor editor = null;
		String pack = Reflection.getPackageName(this.getClass());
		try {
			
			editor = (Editor) Class.forName(pack + "." + name).getConstructor(Entity.class).newInstance(new Entity());
			// editor = (Editor) Class.forName(pack + "." + name).getConstructor().newInstance();
		} catch (Exception e) {
			
		}
		return editor;
	}
	
	public Editor createEditor(String name, Authoring authoringEnvironment, String language )
	{
		Editor editor = null;
		String pack = Reflection.getPackageName(this.getClass());
		try {
			
			editor = (Editor) Class.forName(pack + "." + name).getConstructor(Authoring.class, String.class).newInstance(authoringEnvironment, language);
			// editor = (Editor) Class.forName(pack + "." + name).getConstructor().newInstance();
		} catch (Exception e) {
			
		}
		return editor;
	}
}
