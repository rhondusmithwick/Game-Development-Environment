package gui;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Observable;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class GuiObjectURLOpener extends GuiObject{
		private Button button;
		private URI urlToOpen;
		public GuiObjectURLOpener(String name, String resourceBundle,Observable obs, String urlName) {
			super(name, resourceBundle, obs);
			
			urlToOpen = URI.create(urlName);
			
		}

		@Override
		public Object createObjectAndReturnObject() {
			button = new Button(getResourceBundle().getString(getObjectName()+"BUTTON"));
			button.setOnAction(new EventHandler() {

				@Override
				public void handle(Event arg0) {
					openWebpage(urlToOpen);
					
				}
				
			});
			return button;
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
}