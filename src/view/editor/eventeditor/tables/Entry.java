package view.editor.eventeditor.tables;

import javafx.beans.property.SimpleStringProperty;

/**
 * Entry class, simply contains some object and its name as a SimpleStringProperty.
 * To be used in a Table as data.
 * @author Alankmc
 *
 */
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
