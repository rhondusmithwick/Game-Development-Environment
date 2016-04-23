package view.editor;

import api.IComponent;
import api.IEntity;


public class ComponentFactory {


	public IComponent getComponent(Class<?> clazz, IEntity entity) throws Exception{
		
			if(clazz.getName().endsWith(".Collision")){
				return (IComponent) clazz.getConstructor(String.class).newInstance(entity.getID());
			}
			else{
				return (IComponent) clazz.getConstructor().newInstance();
			}


	}
}
