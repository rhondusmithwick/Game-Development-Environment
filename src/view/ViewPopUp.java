package view;

import java.util.Map;
import java.util.Map.Entry;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import view.utilities.ButtonFactory;

public class ViewPopUp {
	
	public ScrollPane setPopUp(Map<String,EventHandler<ActionEvent>> map){
		VBox box = new VBox();
		for (Entry<String, EventHandler<ActionEvent>> entry : map.entrySet()){
			box.getChildren().add(ButtonFactory.makeButton(entry.getKey(), entry.getValue()));
		}
		return new ScrollPane(box);
	}
	
}
