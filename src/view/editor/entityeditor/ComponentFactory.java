package view.editor.entityeditor;

import api.IComponent;
import api.IEntity;
import voogasalad.util.reflection.Reflection;


public class ComponentFactory {


	public void addComponentToEntity(String name, IEntity entity) {
		if(name.endsWith(".Collision")){
			entity.forceAddComponent((IComponent) Reflection.createInstance(name, entity.getID()), true);
		}else
			entity.forceAddComponent((IComponent) Reflection.createInstance(name), true);


	}
}
