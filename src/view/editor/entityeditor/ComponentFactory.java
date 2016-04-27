package view.editor.entityeditor;

import api.IComponent;
import voogasalad.util.reflection.Reflection;


public class ComponentFactory {


	public IComponent getComponent(String name, Object... args) {
			return (IComponent) Reflection.createInstance(name, args);


	}
}
