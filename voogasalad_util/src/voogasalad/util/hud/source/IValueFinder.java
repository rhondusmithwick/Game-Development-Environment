package voogasalad.util.hud.source;

/**
 * Interface that each of the projects implementing this package
 * must write a separate implementation for. This class tells where
 * to find each of the fields that the user wishes to show in the HUD
 * @author bobby
 *
 */

public interface IValueFinder {
	
	
	/**
	 * Given a string field that the user wishes to show in the HUD,
	 * returns the property inside the game which is associated with
	 * that string
	 * @param key associated with a property inside the game
	 * @return property
	 */
	
	
	public Property find(String key);
	
	/**
	 * Sets a reference to the HUDController
	 * @param the hud controller
	 */
	
	public void setController(HUDController controller);
	
	
	/**
	 * Sets a reference to the main data source class
	 * (which most likely will be a major class in the
	 * game engine)
	 * @param the data source class
	 */
	public void setDataSource(Object dataSource);
	
}
