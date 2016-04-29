package view.editor.eventeditor;

import java.util.ResourceBundle;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;

public class PropertyTable extends Table
{
	public PropertyTable(PropertyTableManager manager, String language)
	{
		super(manager, ResourceBundle.getBundle(language).getString("pickProperty"));

		// Add changeImage listener
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		try{
        		manager.propertyWasClicked((SimpleObjectProperty<?>)observableValue.getValue().getData());
        		} catch (Exception e)
        		{
        			// Do nothing....? I know it's bad code, but all it does it print trace, and continues normally.
        			// TODO: Fix this, it looks horrible.
        		}
        	}
        	);
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
