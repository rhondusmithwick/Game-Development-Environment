package view.editor.eventeditor.tables;

import java.util.ResourceBundle;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Property Table, displaying a certain Component's properties. 
 * @author Alankmc
 *
 */
public class PropertyTable extends Table
{
	public PropertyTable(PropertyTableManager manager, String language) throws NoSuchMethodException, SecurityException
	{

		super(manager, ResourceBundle.getBundle(language).getString("pickProperty"), 
				manager.getClass().getMethod("propertyWasClicked", SimpleObjectProperty.class),
				SimpleObjectProperty.class);	

   	}
	
	@Override
	public void fillEntries(Object dataHolder) 
	{
		for (SimpleObjectProperty<?> property: ((IComponent)dataHolder).getProperties())
		{
			getEntries().add(new Entry(property, property.getName()));
		}
	}
}
