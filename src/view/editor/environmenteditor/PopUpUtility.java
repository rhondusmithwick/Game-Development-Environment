package view.editor.environmenteditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import api.IEntity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.PopUp;

public class PopUpUtility {
	
	private Collection<PopUp> myPopUpList = new ArrayList<PopUp>();
	private EditorEnvironment myControl;
	private ResourceBundle myResources;
	
	   PopUpUtility (EditorEnvironment control, String language) {
		    myResources = ResourceBundle.getBundle(language);
	        myControl = control;
	    }
	
	public void showPopUp(IEntity entity, ContextMenuEvent event) {
		Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<>();
		menuMap.put(myResources.getString("remove"),
				e -> EnvironmentHelperMethods.removeFromDisplay(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendBack"), e -> EnvironmentHelperMethods.sendToBack(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendFront"), e -> EnvironmentHelperMethods.sendToFront(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendBackOne"),
				e -> EnvironmentHelperMethods.sendBackward(entity, myControl.getEntitySystem()));
		menuMap.put(myResources.getString("sendForwardOne"),
				e -> EnvironmentHelperMethods.sendForward(entity, myControl.getEntitySystem()));
		PopUp myPopUp = new PopUp(GUISize.POP_UP_WIDTH.getSize(), GUISize.POP_UP_HIEGHT.getSize());
		myPopUp.show(setPopUp(menuMap), event.getScreenX(), event.getScreenY());
		myPopUpList.add(myPopUp);
	}
	
	public void deletePopUps(MouseEvent e) {
		if (e.getButton() == MouseButton.PRIMARY) {
			myPopUpList.stream().forEach(PopUp::closeScene);
			myPopUpList.clear();
		}
	}

	public ScrollPane setPopUp(Map<String, EventHandler<ActionEvent>> map) {
		VBox box = new VBox();
		for (Entry<String, EventHandler<ActionEvent>> entry : map.entrySet()) {
			Button button = ButtonFactory.makeButton(entry.getKey(), entry.getValue());
			button.setMaxWidth(Double.MAX_VALUE);
			box.getChildren().add(button);
		}
		return new ScrollPane(box);
	}

}
