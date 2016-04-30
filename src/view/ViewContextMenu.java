package view;

import java.util.LinkedHashMap;
import java.util.Map;

import api.IEntity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.utilities.ContextMenuFactory;

public class ViewContextMenu {

	
	
	private void imageViewRightClicked(ImageView imageview, MouseEvent event) {
		imageview.setOnContextMenuRequested(value);
		imageview.setContextMenu(ContextMenuFactory.createContextMenu(makeMenuMap(imageview, event)));
	}
	
	public Map<String,EventHandler<ActionEvent>> makeMenuMap(ImageView imageview, MouseEvent event) {
		Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<String, EventHandler<ActionEvent>>();
		menuMap.put(myResources.getString("remove"), e -> removeFromDisplay(entity, entityButton));
		menuMap.put(myResources.getString("sendBack"), e -> sendToBack(entity));
		menuMap.put(myResources.getString("sendFront"), e -> sendToFront(entity));
		menuMap.put(myResources.getString("sendBackOne"), e ->sendBackward(entity));
		menuMap.put(myResources.getString("sendForwardOne"), e -> sendForward(entity));
		menuMap.put(myResources.getString("saveAsMasterTemplate"), e -> saveToMasterList(entity));
		return menuMap;
	}
	
	
	
}
