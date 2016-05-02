package model.physics;

import api.ICollisionSide;
import api.IEntity;
import javafx.geometry.Bounds;
import model.component.movement.Position;
import model.component.physics.Collision;

public class LeftCollision implements ICollisionSide {

    private static final String SIDE = Collision.LEFT;

    public LeftCollision () {
    }

    public String getSide () {
        return SIDE;
    }

    public void moveEntity (IEntity entityToMove, IEntity entityToStay) {
        entityToMove.getComponent(Position.class)
                .add(-getOverlapWithoutCheck(entityToMove, entityToStay) - 0.1, 0);
    }

    public double getOverlapWithoutCheck (Bounds first, Bounds second) {
        return first.getMaxX() - second.getMinX();
    }

    public boolean isCollision (Bounds first, Bounds second) {
        //System.out.println(first.getMinX() + " " + first.getMaxX() + " " + second.getMinX() + " " + second.getMaxX());
        return first.getMaxX() >= second.getMinX() && first.getMinX() <= second.getMinX();
    }

    public void addCollision (Collision first, Collision second) {
        first.addCollisionSide(Collision.LEFT);
        second.addCollisionSide(Collision.RIGHT);
    }
}


