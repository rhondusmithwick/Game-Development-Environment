package model.component.physics;

import java.util.List;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import utility.SingleProperty;

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
	private SingleProperty<String> maskIDProperty = new SingleProperty<>("MaskID", "");
	private SingleProperty<String> collidingIDsProperty = new SingleProperty<>("CollidingIDs", "");

	public Collision() {
		this("");
	}

	public Collision(Bounds mask, String ID) {
		this.mask = mask;
		this.setMaskID(ID);
	}

	public Collision(String ID) {
		this(null, ID); // TODO: needs to be null?
	}

	public Bounds getMask() {
		return this.mask;
	}

	public void setMask(Bounds mask) {
		this.mask = mask;
	}

	public SimpleObjectProperty<String> maskIDProperty() {
		return maskIDProperty.property1();
	}

	public String getMaskID() {
		return maskIDProperty().get();
	}

	public void setMaskID(String ID) {
		this.maskIDProperty().set(ID);
	}

	public SimpleObjectProperty<String> collidingIDsProperty() {
		return collidingIDsProperty.property1();
	}

	public String getCollidingIDs() {
		return this.collidingIDsProperty().get();
	}

	public void setCollidingIDs(String collidingIDs) {
		this.collidingIDsProperty().set(collidingIDs);
	}

	public void addCollidingID(String collidingIDs) {
		this.collidingIDsProperty().set(this.getCollidingIDs() + "~" + collidingIDs);
	}

	public void addCollisionSide(String side) {
		this.collidingIDsProperty().set(this.getCollidingIDs() + "_" + side);
	}

	public void clearCollidingIDs() {
		this.collidingIDsProperty().set("CollidingIDs");
	}

	@Override
	public List<SimpleObjectProperty<?>> getProperties() {
		// TODO: add maskID property
		return collidingIDsProperty.getProperties();
	}

}
