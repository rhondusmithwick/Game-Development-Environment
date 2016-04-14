package model.component.physics;

import java.util.Collection;
import java.util.HashSet;

import javafx.geometry.Bounds;

import api.IComponent;

public class Collision implements IComponent {
	private Bounds mask;
	private Collection<String> IDs;
	private Collection<String> collidingIDs;

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
