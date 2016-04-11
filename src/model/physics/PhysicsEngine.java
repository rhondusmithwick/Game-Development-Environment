package model.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import api.IPhysicsEngine;
import javafx.scene.shape.Shape;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import api.IEntity;
import api.IEntitySystem;

/**
 * Implementation of the physics engine
 *
 * @author Tom Wu
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
	public boolean applyImpulse(IEntity body, Impulse J) {
		if (body.hasComponent(Velocity.class)) {
			Velocity v = body.getComponent(Velocity.class);
			v.add(J.getJx(), J.getJy());
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
				}
			}
		}

		return false; // TODO
	}

	// probably more useful:
	public Collection<IEntity> getEntitiesCollidingWith(IEntity e) {

		return null; // TODO
	}

	private boolean areIntersectingIDLists(Collection<String> IDList1, Collection<String> IDList2) {
		Set<String> s1 = new HashSet<String>(IDList1);
		Set<String> s2 = new HashSet<String>(IDList2);
		return (Sets.intersection(s1, s2).size() > 0);
	}

	@Override
	public IEntitySystem updateCollisions(IEntitySystem universe, boolean dynamicsOn) {
		Collection<IEntity> collidableEntities = universe.getEntitiesWithComponents(Collision.class);
		for (IEntity firstEntity : collidableEntities) {
			for (IEntity secondEntity : collidableEntities) {
				if (!firstEntity.equals(secondEntity)) {
					// generalize to reduce duplicated code
					Shape firstShape = firstEntity.getComponent(Collision.class).getMask();
					Shape secondShape = secondEntity.getComponent(Collision.class).getMask();
					//getBoundsInLocal or another bound test?
					if (firstShape.intersects(secondShape.getBoundsInLocal())) {
						List<String> firstID = new ArrayList<String>();
						firstID.add(firstEntity.getID());
						secondEntity.getComponent(Collision.class).addCollidingIDs(firstID);
						
						List<String> secondID = new ArrayList<String>();
						secondID.add(secondEntity.getID());
						firstEntity.getComponent(Collision.class).addCollidingIDs(secondID);
					}					
				}

			}
		}
		
		return null;
	}

}
