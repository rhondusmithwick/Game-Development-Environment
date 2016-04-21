package view.editor.eventeditor;

import java.util.ResourceBundle;

import api.IComponent;
import api.ISerializable;
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
	private int propertyIndex;
	private String language;
	private EditorEvent editor;
	
	public TableManager(ObservableList<ISerializable> entityList, String language, EditorEvent editor)
	{
		container = new HBox();
		this.language = language;
		entityTable = new EntityTable(entityList, this, language);
		componentTable = new ComponentTable(this, language);
		propertyTable = new PropertyTable(this, language);
		this.editor = editor;
		
		
		setDefaultTriggerString(null);
		fillLayout();
	}
	
	public void entityWasClicked(Entity entity)
	{
		setDefaultTriggerString(null);
		componentTable.fillEntries(entity);
		this.entity = entity;
	}
	
	public void componentWasClicked(IComponent component)
	{
		setDefaultTriggerString(null);
		propertyTable.fillEntries(component);
		this.component = component;
	}
	
	public void propertyWasClicked(SimpleObjectProperty property)
	{
		propertyIndex = component.getProperties().indexOf(property);
		System.out.println(propertyIndex);
		
		String[] splitClassName = component.getClass().toString().split("\\.");

		editor.triggerSet(entity.getName() + " - " + 
				splitClassName[splitClassName.length - 1] + " - " + 
				property.getName(),
				null);
				//new Trigger(entity.getID(), component, propertyIndex, null) );	// TODO: universe...!?
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
