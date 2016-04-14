package view.editor.eventeditor;

import javafx.beans.property.SimpleStringProperty;

public class Entry 
{
	private SimpleStringProperty name;
	private Object data;
	
	public Entry(Object data, String name)
	{
		
		this.data = data;
		this.name = new SimpleStringProperty(name);
		
	}
	
	public String getName()
	{
		return name.get();
	}
	
	public Object getData()
	{
		return data;
	}
}
