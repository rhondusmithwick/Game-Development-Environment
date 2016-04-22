package view.editor.eventeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import api.IComponent;
import api.IEntity;
import api.ILevel;
import api.ISerializable;
import events.InputSystem;
import events.PropertyTrigger;
import events.Trigger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import model.entity.Entity;

public class TableManager 
{
	private HBox container;
	private EntityTable entityTable;
	private ComponentTable componentTable;
	private PropertyTable propertyTable;
	
	private Entity entity;
	private IComponent component;
	private String propertyName;
	private String language;
	private PropertyEventEditor editor;
	private final InputSystem inputSystem;
	private ObservableList<ISerializable> selectedEntities;
	
	public TableManager(ObservableList<IEntity> entityList, String language, 
			PropertyEventEditor editor, 
			InputSystem inputSystem)
	{
		container = new HBox();
		this.language = language;
		entityTable = new EntityTable(entityList, this, language);
		componentTable = new ComponentTable(this, language);
		propertyTable = new PropertyTable(this, language);
		selectedEntities = FXCollections.emptyObservableList();
		
		this.editor = editor;
		this.inputSystem = inputSystem; 
		
		
		editor.resetTrigger();
		fillLayout();
	}
	
	public void entityWasClicked(Entity entity)
	{
		editor.resetTrigger();
		componentTable.refreshTable();
		propertyTable.refreshTable();
		componentTable.fillEntries(entity);
		this.entity = entity;
	}
	
	public void componentWasClicked(IComponent component)
	{
		editor.resetTrigger();
		propertyTable.refreshTable();
		propertyTable.fillEntries(component);
		this.component = component;
	}
	
	public void propertyWasClicked(SimpleObjectProperty property)
	{
		propertyName = property.getName();
		
		String[] splitClassName = component.getClass().toString().split("\\.");

		editor.triggerSet(entity.getName(), component, property);
	}
	
	public void levelWasPicked(List<ILevel> levels)
	{
		selectedEntities.clear();
		
		for ( ILevel level: levels )
		{
			selectedEntities.addAll(level.getAllEntities());
		}
		
		entityTable.levelWasPicked(selectedEntities);
	}
	
	public void allBoxChanged(String event)
	{
		System.out.println(event);
	}
	
	private void fillLayout()
	{
		container.getChildren().addAll(entityTable.getTable(), componentTable.getTable(), propertyTable.getTable());
	}
	
	public HBox getContainer()
	{
		return container;
	}
	
}
