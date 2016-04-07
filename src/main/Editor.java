package main;

import java.util.ArrayList;
import java.util.List;

import api.IEditor;
import api.IEntity;
import model.entity.EntitySystem;

public abstract class Editor implements IEditor {
	private List<IEntity> entityList;
	
	public Editor(){
		entityList = new ArrayList<IEntity>();
	}
	public List<IEntity> getEntities(){
		return entityList;
		
	}
	public void addEntity(IEntity entity){
		entityList.add(entity);
	}
	
	public void createEntity(){
		EntitySystem entitySystem = new EntitySystem();
		IEntity entity = entitySystem.createEntity();
		addEntity(entity);
	}
	
	public abstract void updateEditor();
}
