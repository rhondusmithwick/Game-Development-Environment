package model.physics;

import api.ICollisionSide;
import api.IEntity;
import javafx.geometry.Bounds;
import model.component.movement.Position;
import model.component.physics.Collision;

public class RightCollision implements ICollisionSide {

    private static final String SIDE = Collision.RIGHT;

    public RightCollision () {
    }

    public String getSide () {
        return SIDE;
    }

    public void moveEntity (IEntity entityToMove, IEntity entityToStay) {
        entityToMove.getComponent(Position.class)
                .add(getOverlapWithoutCheck(entityToMove, entityToStay) + 0.1, 0);
    }

    public double getOverlapWithoutCheck (Bounds first, Bounds second) {
        return -(first.getMinX() - second.getMaxX());
    }

    public boolean isCollision (Bounds first, Bounds second) {
        return first.getMinX() < second.getMaxX() && first.getMaxX() > second.getMaxX();
    }

    public void addCollision (Collision first, Collision second) {
        first.addCollisionSide(Collision.RIGHT);
        second.addCollisionSide(Collision.LEFT);
    }
}


