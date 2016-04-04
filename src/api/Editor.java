package api;

import java.util.ArrayList;
import java.util.List;

import model.entity.Entity;

public abstract class Editor implements IEditor{
	private List<Entity> entityList;
	public Editor(){
		entityList = new ArrayList<Entity>();
	}
	public List<Entity> getEntities(){
		return entityList;
		
	}
	public void addEntity(Entity entity){
		entityList.add(entity);
	}
	
}
