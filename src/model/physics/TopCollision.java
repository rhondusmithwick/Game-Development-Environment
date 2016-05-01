package model.physics;

import api.ICollisionSide;
import api.IEntity;
import javafx.geometry.Bounds;
import model.component.movement.Position;
import model.component.physics.Collision;

public class TopCollision implements ICollisionSide {

	private static final String SIDE = Collision.TOP;
	
	public TopCollision() {
	}
	
	public String getSide() {
		return SIDE;
	}
	
	public void moveEntity(IEntity entityToMove, IEntity entityToStay) {
		entityToMove.getComponent(Position.class)
					.add(0, -getOverlapWithoutCheck(entityToMove, entityToStay)-0.1);	
	}
	
	public double getOverlapWithoutCheck(Bounds first, Bounds second) {
		return first.getMaxY() - second.getMinY();
	}
	
	public boolean isCollision(Bounds first, Bounds second) {
		return first.getMaxY() > second.getMinY() && first.getMinY() < second.getMinY();
	}
	
	public void addCollision(Collision first, Collision second) {
		first.addCollisionSide(Collision.TOP);
		second.addCollisionSide(Collision.BOTTOM);
	}
}
	

