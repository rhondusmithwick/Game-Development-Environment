package model.component.physics;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import utility.SingleProperty;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Roxanne and Tom
 */
@SuppressWarnings("serial")
public class Collision implements IComponent {
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String ID_SEPARATOR = "~";
    public static final String SIDE_SEPARATOR = "_";

    private Bounds mask;
    private final SingleProperty<String> maskIDProperty = new SingleProperty<>("MaskID", "");
    private final SingleProperty<String> collidingIDsProperty = new SingleProperty<>("CollidingIDs", "");

    public Collision () {
    }

    public Collision (Bounds mask, String ID) {
        this.mask = mask;
        this.setMaskID(ID);
    }

    public Collision (String ID) {
        this(null, ID); // TODO: needs to be null?
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

    public Collection<String> getCollidingIDs () {
        String[] collidingEntitiesWithSides = this.collidingIDsProperty().get().split(Collision.ID_SEPARATOR);
        Collection<String> collidingIDs = Arrays.stream(collidingEntitiesWithSides)
                .map(s -> s.split(Collision.SIDE_SEPARATOR))
                .map(t -> t[0])
                .collect(Collectors.toList());
        System.out.println(collidingIDs);
        return collidingIDs;
    }

    public void setCollidingIDs (String collidingIDs) {
        this.collidingIDsProperty().set(collidingIDs);
    }

    public String getCollidingIDsWithSides () {
        return this.collidingIDsProperty().get();
    }

    public void addCollidingID (String collidingIDs) {
        this.collidingIDsProperty().set(this.getCollidingIDs() + Collision.ID_SEPARATOR + collidingIDs);
    }

    public void addCollisionSide (String side) {
        this.collidingIDsProperty().set(this.getCollidingIDs() + Collision.SIDE_SEPARATOR + side);
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
