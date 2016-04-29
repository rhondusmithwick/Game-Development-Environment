package view.editor.eventeditor;

import java.util.ArrayList;
import java.util.List;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import api.ISerializable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.entity.Entity;

public class KeyBindingTableManager extends TableManager
{
	private HBox container;
	private EntityTable entityTable;
	
	private Entity entity;
	private IComponent component;
	private String language;
	private KeyBindingEditor editor;
	private ArrayList<IEntity> pickedEntitiesForEvent;
	private ObservableList<IEntity> selectedEntitiesFromLevel;
	
	public KeyBindingTableManager( String language, KeyBindingEditor editor )
	{
		container = new HBox();
		this.language = language;
		selectedEntitiesFromLevel = FXCollections.observableArrayList();
		entityTable = new EntityTable( selectedEntitiesFromLevel, this, language);
		pickedEntitiesForEvent = new ArrayList<IEntity>();
		
		this.editor = editor;
		
		fillLayout();
	}
	
	public void entityWasClicked(Entity entity)
	{
		if (pickedEntitiesForEvent.contains(entity))
		{
			pickedEntitiesForEvent.remove(entity);
		}
		else
		{
			pickedEntitiesForEvent.add(entity);
		}
		
		editor.choseEntity(pickedEntitiesForEvent);
	}
	
	public void levelWasPicked(List<ILevel> levels)
	{
		selectedEntitiesFromLevel.clear();
		
		for ( ILevel level: levels )
		{
			selectedEntitiesFromLevel.addAll(level.getAllEntities());
		}
		
		entityTable.levelWasPicked(selectedEntitiesFromLevel);
	}
	
	public void allBoxChanged(String event)
	{
		System.out.println(event);
	}
	
	private void fillLayout()
	{
		container.getChildren().addAll(entityTable.getTable());
	}
	
	public HBox getContainer()
	{
		return container;
	}
}
