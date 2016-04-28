package voogasalad.util.hud.source;


import java.util.HashMap;
import java.util.Map;

/**
 * Class that encompasses the model for the HUD
 * @author bobby
 *
 */

public class HUDModel {
	
	private Map<String, Property> data;
	
	public HUDModel(Map<String, Property> data) {
		this.data = data;
	}
	
	public HUDModel() {
		this.data = new HashMap<>();
	}
	
	/**
	 * Returns the map that represents the data of this model
	 * @return data
	 */
	public Map<String, Property> getData() {
		return data;
	}
	
	/**
	 * Handles the ValueChange that is passed to it by the controller.
	 * Does so by updating its map.
	 * @param a valuechange
	 */
	
	public void handleChange(ValueChange change) {
		
		if (data.containsKey(change.getFieldName()) &&
			!data.get(change.getFieldName()).getValue().equals(change.getNewValue())) {
				data.get(change.getFieldName()).setValue(change.getNewValue());
		}
		
	}
	
}
