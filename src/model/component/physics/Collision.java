package model.component.physics;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Shape;
import api.IComponent;

public class Collision implements IComponent {
	private Shape mask;
	private Collection<String> IDs;
	private Collection<String> collidingIDs;

	public Collision(Shape mask, Collection<String> IDs) {
		this.mask = mask;
		this.IDs = IDs;
		this.collidingIDs = new HashSet<String>();
	}

	public Shape getMask() {
		return this.mask;
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
