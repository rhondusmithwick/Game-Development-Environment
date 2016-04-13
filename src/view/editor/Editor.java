package view.editor;

import java.util.ArrayList;
import java.util.List;

import api.IEditor;
import api.IEntity;
import enums.GUISize;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	
	protected Text saveMessage(String message){
		Text text = new Text();
		text.setFont(new Font(GUISize.SAVE_MESSAGE_FONT.getSize()));
		text.setTextAlignment(TextAlignment.CENTER);
		text.setText(message);
		return text;
	}
}
