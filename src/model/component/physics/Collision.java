package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import utility.SingleProperty;

import java.util.List;

/**
 * @author Roxanne and Tom
 */
@SuppressWarnings("serial")
public class Collision implements IComponent {
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String COLLISION_SEPARATOR = "~";
    public static final String ENTITY_SIDE_SEPARATOR = "_";
    
    private Bounds mask;
    private SingleProperty<String> maskIDProperty = new SingleProperty<>("MaskID", "");
    private SingleProperty<String> collidingIDsProperty = new SingleProperty<>("CollidingIDs", "");

    public Collision () {
    }

    public Collision (Bounds mask, String ID) {
        this.mask = mask;
        this.setMaskID(ID);
    }

    public Collision (String ID) {
        this(null, ID);
    }

    public Bounds getMask () {
        return this.mask;
    }

    public void setMask (Bounds mask) {
        this.mask = mask;
    }

    public SimpleObjectProperty<String> maskIDProperty () {
        return maskIDProperty.property1();
    }

    public String getMaskID () {
        return maskIDProperty().get();
    }

    public void setMaskID (String ID) {
        this.maskIDProperty().set(ID);
    }

    public SimpleObjectProperty<String> collidingIDsProperty () {
        return collidingIDsProperty.property1();
    }

    public String getCollidingIDsWithSides () {
        return this.collidingIDsProperty().get().substring(1);
    }

    public void setCollidingIDs (String collidingIDs) {
        this.collidingIDsProperty().set(collidingIDs);
    }

    public void addCollidingID (String collidingIDs) {
    	if(!collidingIDsProperty().get().contains(Collision.COLLISION_SEPARATOR)) {
    		this.collidingIDsProperty().set(collidingIDs);
    	}
    	else {
    		this.collidingIDsProperty().set(this.getCollidingIDsWithSides() + Collision.COLLISION_SEPARATOR + collidingIDs);
    	}
    }

    public void addCollisionSide (String side) {
        this.collidingIDsProperty().set(this.getCollidingIDsWithSides() + Collision.ENTITY_SIDE_SEPARATOR + side);
    }

    public void clearCollidingIDs () {
        this.collidingIDsProperty().set("");
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        // TODO: add maskID property
        return collidingIDsProperty.getProperties();
    }

    @Override
    public void update () {
        setCollidingIDs(getCollidingIDsWithSides());
        setMask(getMask());
        setMaskID(getMaskID());
    }

}