package gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;


public class GuiObjectFileOpener extends GuiObject{
	private Button button;
	private String fileToOpen;
	private static final String FILE_DIR = "files/";
	
	public GuiObjectFileOpener(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial) {
		super(name, resourceBundle);
		fileToOpen = getResourceBundle().getString(name+"file");
		button = new Button(getResourceBundle().getString(getObjectName()+"Button"));

		
	}

	@Override
	public Object getCurrentValue() {
		return null;
	}

	@Override
	public Control getControl() {
		return button;
	}

	@Override
	public Object getGuiNode() {
		button.setOnAction(new EventHandler() {
            @Override
			public void handle(Event t) {

			    File file = new File(FILE_DIR+fileToOpen);
			    if (file.exists())
			    {
			     if (Desktop.isDesktopSupported())
			     {
			      try
			      {
			       Desktop.getDesktop().open(file);
			      }
			      catch (IOException e)
			      {
			      
			       e.printStackTrace();
			      }
			     }
			     else
			      {
			       System.out.println("Awt Desktop is not supported!");
			      }
			    }
			    
			    else
			    {
			     System.out.println("File does not exist!");
			    }
			  
			   }
			  });
			  
		return button;
	}

	



}
