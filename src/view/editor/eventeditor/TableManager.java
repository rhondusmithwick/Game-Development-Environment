package view.editor.eventeditor;

import java.util.ResourceBundle;

import api.IComponent;
import api.IEntity;
import api.ISerializable;
import events.InputSystem;
import events.PropertyTrigger;
import events.Trigger;
import javafx.beans.property.SimpleObjectProperty;
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
	
	public TableManager(ObservableList<IEntity> entityList, String language, 
			PropertyEventEditor editor, 
			InputSystem inputSystem)
	{
		container = new HBox();
		this.language = language;
		entityTable = new EntityTable(entityList, this, language);
		componentTable = new ComponentTable(this, language);
		propertyTable = new PropertyTable(this, language);
		
		this.editor = editor;
		this.inputSystem = inputSystem; 
		
		
		setDefaultTriggerString(null);
		fillLayout();
	}
	
	public void entityWasClicked(Entity entity)
	{
		setDefaultTriggerString(null);
		componentTable.refreshTable();
		propertyTable.refreshTable();
		componentTable.fillEntries(entity);
		this.entity = entity;
	}
	
	public void componentWasClicked(IComponent component)
	{
		setDefaultTriggerString(null);
		propertyTable.refreshTable();
		propertyTable.fillEntries(component);
		this.component = component;
	}
	
	public void propertyWasClicked(SimpleObjectProperty property)
	{
		propertyName = property.getName();
		
		String[] splitClassName = component.getClass().toString().split("\\.");

		editor.triggerSet(entity.getName() + " - " + 
				splitClassName[splitClassName.length - 1] + " - " + 
				property.getName(),
				new PropertyTrigger(entity.getID(), component.getClass(), propertyName)
				);	
	
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
	
	private void setDefaultTriggerString(Trigger trigger)
	{
		editor.triggerSet(ResourceBundle.getBundle(language).getString("notYetDefined"), trigger);
	}
	
}
