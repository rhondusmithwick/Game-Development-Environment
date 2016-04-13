package view.editor.eventeditor;

import api.IComponent;
import javafx.beans.property.SimpleStringProperty;

public class ComponentEntry 
{

	IComponent component;
	private SimpleStringProperty name;

	public ComponentEntry(IComponent component)
	{
		this.component = component;
	
		String[] splitClassName = component.getClass().toString().split("\\.");
		
		name = new SimpleStringProperty(splitClassName[splitClassName.length - 1]);
	}

	public IComponent getComponent()
	{
		return component;
	}

	public String getName()
	{
		return name.get();
	}


}
