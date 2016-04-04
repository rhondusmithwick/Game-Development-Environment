package model.component.physics;

import model.component.IComponent;
import javafx.scene.shape.Shape;

import java.util.Collection;


public class Collision implements IComponent {
    private Shape mask;
    private Collection<String> IDs;

    public Collision(Shape mask, Collection<String> IDs) {
        this.mask = mask;
        this.IDs = IDs;
    }

    public Shape getMask() {
        return this.mask;
    }

    public Collection<String> getIDs() {
        return this.IDs;
    }

}
