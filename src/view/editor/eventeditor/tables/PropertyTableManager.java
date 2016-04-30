package view.editor.eventeditor.tables;

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
import view.editor.eventeditor.tabs.PropertyEventEditor;

/**
 * Table manager that will control everyTable in the PropertyEventEditor Tab.
 * @author Alankmc
 *
 */
public class PropertyTableManager extends TableManager 
{
	private HBox container;
	private EntityTable entityTable;
	private ComponentTable componentTable;
	private PropertyTable propertyTable;
	
	private Entity entity;
	private IComponent component;
	private String language;
	private PropertyEventEditor editor;
	
	private ObservableList<IEntity> selectedEntities;

	public PropertyTableManager(String language, PropertyEventEditor editor )
	{
		container = new HBox();
		this.language = language;
		selectedEntities = FXCollections.observableArrayList();


		try {
			entityTable = new EntityTable(selectedEntities, this, language);
			componentTable = new ComponentTable(this, language);
			propertyTable = new PropertyTable(this, language);

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		this.editor = editor;

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
	
	public void propertyWasClicked(SimpleObjectProperty<?> property)
	{
		editor.triggerSet(entity.getName(), component, property);
	}
	
	public void levelWasPicked(List<ILevel> levels)
	{
		selectedEntities.clear();
		componentTable.refreshTable();
		propertyTable.refreshTable();
		
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
