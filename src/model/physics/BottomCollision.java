package model.physics;

import api.ICollisionSide;
import api.IEntity;
import javafx.geometry.Bounds;
import model.component.movement.Position;
import model.component.physics.Collision;

public class BottomCollision implements ICollisionSide {

    private static final String SIDE = Collision.BOTTOM;

    public BottomCollision () {
    }

    public String getSide () {
        return SIDE;
    }

    public void moveEntity (IEntity entityToMove, IEntity entityToStay) {
        entityToMove.getComponent(Position.class)
                .add(0, getOverlapWithoutCheck(entityToMove, entityToStay) + 0.1);
    }

    public double getOverlapWithoutCheck (Bounds first, Bounds second) {
        return -(first.getMinY() - second.getMaxY());
    }

    public boolean isCollision (Bounds first, Bounds second) {
        return first.getMinY() < second.getMaxY() && first.getMaxY() > second.getMaxY();
    }

    public void addCollision (Collision first, Collision second) {
        first.addCollisionSide(Collision.BOTTOM);
        second.addCollisionSide(Collision.TOP);
    }
}


