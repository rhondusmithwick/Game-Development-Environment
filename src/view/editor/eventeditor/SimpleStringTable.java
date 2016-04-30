package view.editor.eventeditor;

import java.util.ResourceBundle;
import java.util.Collection;

import api.IComponent;
import model.entity.Entity;

public class SimpleStringTable extends Table
{

	public SimpleStringTable(TableManager manager, String name) {
		super(manager, name, null, null);
		
		/*
		getTable().
        getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> 
        	{
        		try
        		{
        			manager.componentWasClicked((IComponent)observableValue.getValue().getData());
        		} catch (Exception e)
        		{
        			// TODO BAAAAAAD
        		}
        	}
        	);
        	*/
	}

	/**
	 * Pass in a ArrayList of Strings.
	 */
	
	@Override
	public void fillEntries(Object dataHolder) 
	{
		for (String string: ((Collection<String>)dataHolder) )
		{
			getEntries().add(new Entry(string, string));
		}
	}

}
