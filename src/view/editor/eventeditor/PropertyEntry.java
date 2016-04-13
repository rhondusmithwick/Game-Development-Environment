package view.editor.eventeditor;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PropertyEntry 
{
	SimpleObjectProperty property;
	private SimpleStringProperty name;

	public PropertyEntry(SimpleObjectProperty property)
	{
		this.property = property;
	
		name = new SimpleStringProperty(property.getName());
	}

	public SimpleObjectProperty getProperty()
	{
		return property;
	}

	public String getName()
	{
		return name.get();
	}

}
