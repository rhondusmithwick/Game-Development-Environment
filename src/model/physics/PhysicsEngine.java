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
	IEntitySystem settings;

	public PhysicsEngine(IEntitySystem settings) {
		this.settings = settings;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(IEntitySystem universe, double dt) {
		
		Collection<IEntity> dynamicEntities = universe.getEntitiesWithComponents(Position.class, Velocity.class, ImagePath.class);
		dynamicEntities.stream().forEach(p -> {
			Position pos = p.getComponent(Position.class);
			Velocity velocity = p.getComponent(Velocity.class);
			ImageView imageView = p.getComponent(ImagePath.class).getImageView();
			imageView.setX(pos.getX());
			imageView.setY(pos.getY());
			double dx = dt * velocity.getVX();
			double dy = dt * velocity.getVY();
			pos.add(dx, dy);
		});
		applyCollisions(universe, true); // WHAT'S PURPOSE OF BOOLEAN HERE?
		applyGravity(universe, dt);
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

	// might be useless:
	public boolean areCollidingEntities(IEntity e1, IEntity e2) {
		List<Collision> cList1 = e1.getComponentList(Collision.class);
		List<Collision> cList2 = e2.getComponentList(Collision.class);
		for (Collision c1 : cList1) {
			Bounds mask1 = c1.getMask();
			Collection<String> IDList1 = c1.getIDs();
			for (Collision c2 : cList2) {
				Bounds mask2 = c2.getMask();
				Collection<String> IDList2 = c2.getIDs();
				if (!this.areIntersectingIDLists(IDList1, IDList2)) {
					// TODO
					return true;
				}
			}
		}
		return false; // TODO
	}

	// probably more useful:
	public Collection<IEntity> getEntitiesCollidingWith(IEntity e) {
		// returns an collection of entities being collided with
		// if not colliding with any entities, collection is empty
		
		Collection<IEntity> collidingEntities = new HashSet<IEntity>();
		for (String collidingID : e.getComponent(Collision.class).getCollidingIDs()) {
			collidingEntities.add(settings.getEntity(collidingID));
		}
		
		return collidingEntities;
	}

	private boolean areIntersectingIDLists(Collection<String> IDList1, Collection<String> IDList2) {
		Set<String> s1 = new HashSet<String>(IDList1);
		Set<String> s2 = new HashSet<String>(IDList2);
		return (Sets.intersection(s1, s2).size() > 0);
	}	
	
	public void applyGravity(IEntitySystem universe, double secondsPassed) {
		Collection<IEntity> entitiesSubjectToGravity = universe.getEntitiesWithComponents(Gravity.class, Velocity.class);
		for (IEntity entity : entitiesSubjectToGravity) {
			Velocity entityVelocity = entity.getComponent(Velocity.class);
			double gravity = entity.getComponent(Gravity.class).getGravity();
			entityVelocity.setVXY(entityVelocity.getVX(), gravity*secondsPassed);
		}		
	}
	
	

	@Override
	public void applyCollisions(IEntitySystem universe, boolean dynamicsOn) {
		List<IEntity> collidableEntities = new ArrayList<IEntity>(universe.getEntitiesWithComponents(Collision.class));
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
					System.out.println("COLLISION OCCURRED");
					addCollisionID(firstEntity, secondEntity);
					addCollisionID(secondEntity, firstEntity);
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
		//CALCULATE COEFFICIENT OF RESTITUTION
		int restitution = 0;
		setNewVelocityForEntity(firstEntity, secondEntity, restitution);
		setNewVelocityForEntity(secondEntity, firstEntity, restitution);
	}
	
	private void setNewVelocityForEntity(IEntity firstEntity, IEntity secondEntity, double restitution) {
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
	
	private void addCollisionID(IEntity entityAddingTo, IEntity entityAddingFrom) {
		entityAddingTo.getComponent(Collision.class).addCollidingID(entityAddingFrom.getID());
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
