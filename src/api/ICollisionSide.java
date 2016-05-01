package api;

import api.IEntity;
import javafx.geometry.Bounds;
import model.component.physics.Collision;

public interface ICollisionSide {
	
	public void moveEntity(IEntity entityToMove, IEntity entityToStay);
	
	boolean isCollision(Bounds first, Bounds second);
	
	void addCollision(Collision first, Collision second);
	
	String getSide();
	
	double getOverlapWithoutCheck(Bounds first, Bounds second);
	
	default double getOverlapWithoutCheck(IEntity entityToMove, IEntity entityToStay) {
		return getOverlapWithoutCheck(entityToMove.getComponent(Collision.class).getMask(),
				entityToStay.getComponent(Collision.class).getMask());
	}
	
	default double getOverlap(Bounds first, Bounds second) {
		if (isCollision(first, second)) {
			return getOverlapWithoutCheck(first, second);
		}
		return Double.MAX_VALUE;
	}
	
	
}
