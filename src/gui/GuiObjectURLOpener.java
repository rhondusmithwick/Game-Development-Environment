package gui;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import api.ISerializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GuiObjectURLOpener extends GuiObject{
		private Button button;
		private URI urlToOpen;
		public GuiObjectURLOpener(String name, String resourceBundle,EventHandler<ActionEvent> event, Property<?> property, ListProperty<?> list, ISerializable serial) {
			super(name, resourceBundle);
			
			urlToOpen = URI.create(getResourceBundle().getString(name+"URL"));
			button = new Button(getResourceBundle().getString(getObjectName()+"Button"));
			addHandler();
			
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void addHandler() {
			button.setOnAction(new EventHandler() {

				@Override
				public void handle(Event arg0) {
					openWebpage(urlToOpen);
					
				}
				
			});			
		}


		public static void openWebpage(URI uri) {
		    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
		        try {
		            desktop.browse(uri);
		        } catch (Exception e) {
		            e.printStackTrace();
		        } 	
		    }
		}

		public static void openWebpage(URL url) {
		    try {
		        openWebpage(url.toURI());
		    } catch (URISyntaxException e) {
		        e.printStackTrace();
		    }
		}

		@Override
		public Object getCurrentValue() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getGuiNode() {
			// TODO Auto-generated method stub
			return button;
		}
}