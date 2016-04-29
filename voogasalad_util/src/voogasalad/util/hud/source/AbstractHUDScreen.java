package voogasalad.util.hud.source;

import javafx.scene.SubScene;

/**
 * 
 * Abstract class for HUD screens.
 * 
 * @author bobby
 *
 */


public abstract class AbstractHUDScreen {
	/**
	 * Handles a ValueChange which is passed to it. Implementations of 
	 * AbstractHUDScreen will modify the view with this method.
	 * @param a valuechange
	 */
	public abstract void handleChange(ValueChange change);
	
	/**
	 * Initializes the view
	 */
	
	public abstract void init();
	
	
	/*
	 * Returns the subscene which this view is embedded into
	 */
	
	public abstract SubScene getScene();
}
