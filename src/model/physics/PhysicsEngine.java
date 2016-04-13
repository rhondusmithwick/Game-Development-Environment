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
import javafx.scene.shape.Shape;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Mass;
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
	public IEntitySystem update(IEntitySystem universe, double dt) {
		Collection<IEntity> dynamicEntities = universe.getEntitiesWithComponents(Position.class, Velocity.class);
		dynamicEntities.stream().forEach(p -> {
			Position pos = p.getComponent(Position.class);
			Velocity velocity = p.getComponent(Velocity.class);
			double dx = dt * velocity.getVX();
			double dy = dt * velocity.getVY();
			pos.add(dx, dy);
		});
		return universe;
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
			Shape mask1 = c1.getMask();
			Collection<String> IDList1 = c1.getIDs();
			for (Collision c2 : cList2) {
				Shape mask2 = c2.getMask();
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

	@Override
	public IEntitySystem updateCollisionComponents(IEntitySystem universe, boolean dynamicsOn) {
		//more efficient if can get List instead of Collection
		Collection<IEntity> collidableEntities = universe.getEntitiesWithComponents(Collision.class);
		
		for (IEntity firstEntity : collidableEntities) {
			for (IEntity secondEntity : collidableEntities) {
				if (!firstEntity.equals(secondEntity)) {
					
					List<Shape> firstHitBoxes = getHitBoxesForEntity(firstEntity);
					List<Shape> secondHitBoxes = getHitBoxesForEntity(secondEntity);

					//getBoundsInParent?
					for (Shape firstHitBox : firstHitBoxes) {
						for (Shape secondHitBox : secondHitBoxes) {
							if (firstHitBox.intersects(secondHitBox.getBoundsInParent())) {
								//LATER:  ADD SOMETHING TO DIFFERENTIATE MASK, NOT JUST ENTITY
								addCollisionComponents(firstEntity, secondEntity);
								addCollisionComponents(secondEntity, firstEntity);								
							}
						}
					}					
				}
			}
		}
		return universe;
	}
	
	public void changeVelocityAfterCollision(IEntity firstEntity, IEntity secondEntity) {		
		//CALCULATE COEFFICIENT OF RESTITUTION
		int restitution = 0;
		
		setNewVelocityForEntity(firstEntity, secondEntity, restitution);
		setNewVelocityForEntity(secondEntity, firstEntity, restitution);
	}
	
	private void setNewVelocityForEntity(IEntity entityToSet, IEntity entityCollidingWith, double restitution) {
		double nextVX1 = getNextVelocityComponent(entityToSet, entityCollidingWith, restitution, (Velocity v) -> v.getVX());
		double nextVY1 = getNextVelocityComponent(entityToSet, entityCollidingWith, restitution, (Velocity v) -> v.getVY());
		entityToSet.getComponent(Velocity.class).setVXY(nextVX1, nextVY1);		
	}
	
	private double getNextVelocityComponent(IEntity firstEntity, IEntity secondEntity, double restitution, Function<Velocity, Double> getCoordinate) {
		double mass1 = firstEntity.getComponent(Mass.class).getMass();
		double velocity1 = getCoordinate.apply(firstEntity.getComponent(Velocity.class));
		
		double mass2 = secondEntity.getComponent(Mass.class).getMass();
		double velocity2 = getCoordinate.apply(secondEntity.getComponent(Velocity.class));
		
		double velocityWithoutRestitution = getVelocityWithoutRestitution(mass1, mass2, velocity1, velocity2);
		return velocityWithoutRestitution + ((mass2 * restitution * (velocity2 - velocity1)) / (mass1 + mass2));
	}
	
	private double getVelocityWithoutRestitution(double mass1, double mass2, double velocity1, double velocity2) {
		return (mass1 * velocity1) + (mass2 * velocity2) / (mass1 + mass2);
	}
	
	public void applyGravity(IEntitySystem universe, double timePassed) {
		//will need to apply only to entities that are "prone" to gravity
		//ex: floating platforms not prone to gravity
		//NEED AMOUNT OF TIME THAT HAS PASSED
		//assume time passed is in milliseconds (NEED TO FIGURE THIS OUT FOR SURE)
		
	}
	
	private void addCollisionComponents(IEntity entityAddingTo, IEntity entityAddingFrom) {
		List<String> entityID = new ArrayList<String>();
		entityID.add(entityAddingFrom.getID());
		entityAddingTo.getComponent(Collision.class).addCollidingIDs(entityID);
	}
	
	private List<Shape> getHitBoxesForEntity(IEntity entity) {
		List<Collision> collisionComponents = entity.getComponentList(Collision.class);
		
		List<Shape> hitBoxes = new ArrayList<>();
		for (Collision hitBox : collisionComponents) {
			hitBoxes.add(hitBox.getMask());
		}
		return hitBoxes;
	}

}
