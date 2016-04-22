package model.component.physics;

import java.util.Collection;
import java.util.HashSet;

import api.IComponent;
import javafx.geometry.Bounds;

/**
 * 
 * @author Roxanne and Tom
 *
 */
public class Collision implements IComponent {	
	public static final String TOP = "top";
	public static final String BOTTOM = "bottom";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	
	private Bounds mask;
	private Collection<String> IDs;
	private Collection<String> collidingIDs;
	// TEMPORARY UNTIL IDS CONVERTED TO STRING PROPERTY
	public String collidingSide = "";

	public Collision(Bounds mask, Collection<String> IDs) {
		this.mask = mask;
		this.IDs = IDs;
		this.collidingIDs = new HashSet<String>();
	}

	public Collision(Collection<String> IDs) {
		this.IDs = IDs;
		this.collidingIDs = new HashSet<String>();
	}

	public Bounds getMask() {
		return this.mask;
	}

	public void setMask(Bounds mask) {
		this.mask = mask;
	}

	public Collection<String> getIDs() {
		return this.IDs;
	}

	public void addIDs(Collection<String> newIDs) {
		this.IDs.addAll(newIDs);
	}

	public void removeIDs(Collection<String> IDs) {
		this.IDs.removeAll(IDs);
	}

	public void clearIDs() {
		this.IDs.clear();
	}

	public Collection<String> getCollidingIDs() {
		return this.collidingIDs;
	}

	public void addCollidingIDs(Collection<String> newIDs) {
		this.collidingIDs.addAll(newIDs);
	}

	public void addCollidingID(String newID) {
		this.collidingIDs.add(newID);
	}

	public void clearCollidingIDs() {
		this.collidingIDs.clear();
	}

}
