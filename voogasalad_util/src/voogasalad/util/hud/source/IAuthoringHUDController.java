package voogasalad.util.hud.source;

/**
 * Interface that the authoring environment must implement.
 * @author bobby
 *
 */

public interface IAuthoringHUDController {
	
	/**
	 * Sets the value of an internal variable
	 * to the filepath that is returned
	 * from the popup window
	 * @param location
	 */
	
	public void setHUDInfoFile(String location);
}