package model.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.google.common.collect.Sets;
import api.IPhysicsEngine;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Gravity;
import model.component.physics.Mass;
import model.component.visual.ImagePath;
import api.IEntity;
import api.IEntitySystem;

/**
 * Implementation of the physics engine
 *
 * @author Tom Wu and Roxanne Baker
 */
public class PhysicsEngine implements IPhysicsEngine {

	private boolean gravityActive;
	private boolean collisionDetectionActive;
	private boolean frictionActive;
	
	
	public PhysicsEngine() {
		gravityActive = true;
		collisionDetectionActive = true;
		frictionActive = true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(IEntitySystem universe, double dt) {
		Collection<IEntity> dynamicEntities = universe.getEntitiesWithComponents(Position.class, Velocity.class, ImagePath.class);
		dynamicEntities.stream().forEach(p -> {
			Position pos = p.getComponent(Position.class);
			ImageView imageView = p.getComponent(ImagePath.class).getImageView();
			imageView.setTranslateX(pos.getX());
			imageView.setTranslateY(pos.getY());
		});
		
		if (collisionDetectionActive) {
			applyCollisions(universe, true);	
		}
		if (gravityActive) {
			applyGravity(universe, dt);			
		}

		dynamicEntities.stream().forEach(p -> {
			Position pos = p.getComponent(Position.class);
			Velocity velocity = p.getComponent(Velocity.class);
			double dx = dt * velocity.getVX();
			double dy = dt * velocity.getVY();
			pos.add(dx, dy);
		});
	}

	@Override
	public boolean applyImpulse(IEntity body, Vector vector) {
		if (body.hasComponent(Velocity.class)) {
			Velocity v = body.getComponent(Velocity.class);
			v.add(vector.getXComponent(), vector.getYComponent());
			return true;
		} else {
			return false;
		}
	}
	
	public void applyGravity(IEntitySystem universe, double secondsPassed) {
		Collection<IEntity> entitiesSubjectToGravity = universe.getEntitiesWithComponents(Gravity.class, Velocity.class);
		entitiesSubjectToGravity.stream().forEach(entity -> {
			Velocity entityVelocity = entity.getComponent(Velocity.class);
			double gravity = entity.getComponent(Gravity.class).getGravity();
			entityVelocity.setVXY(entityVelocity.getVX(), gravity*secondsPassed);
		});	
	}


	@Override
	public void applyCollisions(IEntitySystem universe, boolean dynamicsOn) {
		List<IEntity> collidableEntities = new ArrayList<IEntity>(universe.getEntitiesWithComponents(Collision.class, ImagePath.class));
		clearCollisionComponents(collidableEntities);
		
		for (int i=0; i<collidableEntities.size(); i++) {
			for (int j=i+1; j<collidableEntities.size(); j++) {
				addCollisionComponents(collidableEntities.get(i), collidableEntities.get(j));
			}
		}
	}
	
	private void addCollisionComponents(IEntity firstEntity, IEntity secondEntity) {
		List<Bounds> firstHitBoxes = getHitBoxesForEntity(firstEntity);
		List<Bounds> secondHitBoxes = getHitBoxesForEntity(secondEntity);

		for (Bounds firstHitBox : firstHitBoxes) {
			for (Bounds secondHitBox : secondHitBoxes) {
				if (firstHitBox.intersects(secondHitBox)) {
					addCollisionIDs(firstEntity, secondEntity);
					changeVelocityAfterCollision(firstEntity, secondEntity);
					break;
				}
			}
		}			
	}
	
	private void clearCollisionComponents(List<IEntity> collidableEntities) {
		for (IEntity entity : collidableEntities) {
			entity.getComponent(Collision.class).clearCollidingIDs();
			entity.getComponent(Collision.class).setMask(entity.getComponent(ImagePath.class).getImageView().getBoundsInParent());
		}
	}
	
	public void changeVelocityAfterCollision(IEntity firstEntity, IEntity secondEntity) {		
		//CALCULATE COEFFICIENT OF RESTITUTION - MAKE IT A COMPONENT FOR EACH ENTITY
		double restitution = 0.5;
		
		// change to set X and Y components separately; will likely require "setVX" and "setVY" methods
		double nextVX1 = getNextVelocityComponent(firstEntity, secondEntity, restitution, (Velocity v) -> v.getVX());
		double nextVY1 = getNextVelocityComponent(firstEntity, secondEntity, restitution, (Velocity v) -> v.getVY());
		
		double nextVX2 = getNextVelocityComponent(secondEntity, firstEntity, restitution, (Velocity v) -> v.getVX());
		double nextVY2 = getNextVelocityComponent(secondEntity, firstEntity, restitution, (Velocity v) -> v.getVY());
		
		firstEntity.getComponent(Velocity.class).setVXY(nextVX1, nextVY1);	
		secondEntity.getComponent(Velocity.class).setVXY(nextVX2, nextVY2);
	}
	
	private double getNextVelocityComponent(IEntity firstEntity, IEntity secondEntity, double restitution, Function<Velocity, Double> getCoordinate) {
		double mass1 = firstEntity.getComponent(Mass.class).getMass();
		double velocity1 = getCoordinate.apply(firstEntity.getComponent(Velocity.class));
		
		double mass2 = secondEntity.getComponent(Mass.class).getMass();
		double velocity2 = getCoordinate.apply(secondEntity.getComponent(Velocity.class));
		
		double velocityBeforeRestitution = getVelocityBeforeRestitution(mass1, mass2, velocity1, velocity2);
		double finalVelocity = velocityBeforeRestitution + ((mass2 * restitution * (velocity2 - velocity1)) / (mass1 + mass2));
		return finalVelocity;
	}
	
	private double getVelocityBeforeRestitution(double mass1, double mass2, double velocity1, double velocity2) {
		return ((mass1 * velocity1) + (mass2 * velocity2)) / (mass1 + mass2);
	}
	
	private void addCollisionIDs(IEntity firstEntity, IEntity secondEntity) {
		firstEntity.getComponent(Collision.class).addCollidingID(secondEntity.getID());
		secondEntity.getComponent(Collision.class).addCollidingID(firstEntity.getID());
	}
	
	private List<Bounds> getHitBoxesForEntity(IEntity entity) {
		List<Collision> collisionComponents = entity.getComponentList(Collision.class);
		List<Bounds> hitBoxes = new ArrayList<>();
		for (Collision hitBox : collisionComponents) {
			hitBoxes.add(hitBox.getMask());
		}
		return hitBoxes;
	}
}
