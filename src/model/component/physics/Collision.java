package model.component.physics;

import java.util.Collection;
import api.IComponent;
import javafx.scene.shape.Shape;


public class Collision implements IComponent {
    private Shape mask;
    private Collection<String> IDs;

    public Collision (Shape mask, Collection<String> IDs) {
        this.mask = mask;
        this.IDs = IDs;
    }

    public Shape getMask () {
        return this.mask;
    }

    public Collection<String> getIDs () {
        return this.IDs;
    }

}
