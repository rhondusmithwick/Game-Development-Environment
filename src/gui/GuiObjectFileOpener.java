package gui;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;


public class GuiObjectFileOpener extends GuiObject{
	private Button button;
	private String fileToOpen;
	private static final String FILE_DIR = "files/";
	public GuiObjectFileOpener(String name, String resourceBundle,Observable obs, String fileName) {
		super(name, resourceBundle, obs);
		fileToOpen = fileName;
		
	}

	@Override
	public Object createObjectAndReturnObject() {
		button = new Button(getResourceBundle().getString(getObjectName()+"BUTTON"));
		button.setOnAction(new EventHandler() {
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
