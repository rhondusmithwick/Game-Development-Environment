package view.editor;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import model.entity.EntitySystem;
import view.Editor;

public class EditorEnvironment extends Editor{
	
	EntitySystem myEntities;

	@Override
	public void loadDefaults() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Group getGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateLayout(Pane pane) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEditor() {
		// TODO Auto-generated method stub
		
	}

	public void addEntitySystem(EntitySystem entitySystem) {
		myEntities = entitySystem;
	}

	public EntitySystem getEntitySystem() {
		return myEntities;
	}

	public Object getEntity(int i) {
		return myEntities.getEntity(i);
	}

}
