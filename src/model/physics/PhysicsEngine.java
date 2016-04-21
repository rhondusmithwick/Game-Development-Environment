package model.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import api.IEntity;
import api.ILevel;
import api.ILevel;
import api.IPhysicsEngine;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Friction;
import model.component.physics.Gravity;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;
import model.component.visual.ImagePath;

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
	public void update(ILevel universe, double dt) {
		Collection<IEntity> dynamicEntities = universe.getEntitiesWithComponents(Position.class, Velocity.class,
				ImagePath.class);
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
		if (frictionActive) {
			applyFriction(universe, dt);
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

	public void applyGravity(ILevel universe, double secondsPassed) {
		Collection<IEntity> entitiesSubjectToGravity = universe.getEntitiesWithComponents(Gravity.class,
				Velocity.class);
		entitiesSubjectToGravity.stream().forEach(entity -> {
			Velocity entityVelocity = entity.getComponent(Velocity.class);
			double gravity = entity.getComponent(Gravity.class).getGravity();
			entityVelocity.setVXY(entityVelocity.getVX(), gravity * secondsPassed);
		});
	}

	@Override
	public void applyCollisions(ILevel universe, boolean dynamicsOn) {
		List<IEntity> collidableEntities = new ArrayList<IEntity>(
				universe.getEntitiesWithComponents(Collision.class, ImagePath.class, RestitutionCoefficient.class, Velocity.class));
		clearCollisionComponents(collidableEntities);

		for (int i = 0; i < collidableEntities.size(); i++) {
			for (int j = i + 1; j < collidableEntities.size(); j++) {
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
			entity.getComponent(Collision.class)
					.setMask(entity.getComponent(ImagePath.class).getImageView().getBoundsInParent());
		}
	}

	public void changeVelocityAfterCollision(IEntity firstEntity, IEntity secondEntity) {
		// CALCULATE COEFFICIENT OF RESTITUTION - MAKE IT A COMPONENT FOR EACH
		// ENTITY
		double restitution = (firstEntity.getComponent(RestitutionCoefficient.class).getRestitutionCoefficient()
				+ secondEntity.getComponent(RestitutionCoefficient.class).getRestitutionCoefficient()) / 2;

		double mass1 = firstEntity.getComponent(Mass.class).getMass();
		Velocity velocity1 = firstEntity.getComponent(Velocity.class);

		double mass2 = secondEntity.getComponent(Mass.class).getMass();
		Velocity velocity2 = secondEntity.getComponent(Velocity.class);

		setVelocityComponent(mass1, mass2, velocity1, velocity2, restitution, (Velocity v) -> v.getVX(),
				(Velocity v, Double val) -> v.setVX(val));
		setVelocityComponent(mass1, mass2, velocity1, velocity2, restitution, (Velocity v) -> v.getVY(),
				(Velocity v, Double val) -> v.setVY(val));
	}

	private void setVelocityComponent(double mass1, double mass2, Velocity velocity1, Velocity velocity2,
			double restitution, Function<Velocity, Double> getCoordinate, BiConsumer<Velocity, Double> setVelocity) {
		double initialVelocity1 = getCoordinate.apply(velocity1);
		double initialVelocity2 = getCoordinate.apply(velocity2);

		double velocityBeforeRestitution = getVelocityBeforeRestitution(mass1, mass2, initialVelocity1,
				initialVelocity2);
		double finalVelocity1 = velocityBeforeRestitution
				+ ((mass2 * restitution * (initialVelocity2 - initialVelocity1)) / (mass1 + mass2));
		double finalVelocity2 = velocityBeforeRestitution
				+ ((mass1 * restitution * (initialVelocity1 - initialVelocity2)) / (mass1 + mass2));

		setVelocity.accept(velocity1, finalVelocity1);
		setVelocity.accept(velocity2, finalVelocity2);
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
	
	private void applyFriction(IEntitySystem universe, double secondsPassed) {
		//coefficient of friction is 0.62
		//only applied for things that slide
		//not for player characters, but for objects getting pushed around
		List<IEntity> frictionProneEntities = new ArrayList<IEntity>(
				universe.getEntitiesWithComponents(Friction.class, Gravity.class, Velocity.class));
		for (IEntity entity : frictionProneEntities) {
			double friction = entity.getComponent(Friction.class).getFriction();
			double gravity = entity.getComponent(Gravity.class).getGravity();
			double acceleration = friction*gravity;
			Velocity velocity = entity.getComponent(Velocity.class);
			velocity.add(Math.max(- velocity.getVX(), -acceleration*secondsPassed), 0);
		}
	}
	
	private void getSideOfCollision(Position firstEntityPos, Position secondEntityPos) {
		Vector entityOneToTwo = new Vector(firstEntityPos.getX()-secondEntityPos.getX(),
											firstEntityPos.getX()-secondEntityPos.getY());
		Vector referenceVector = new Vector(0, 1);
		double angle = Math.acos(getDotProduct(entityOneToTwo, referenceVector));
		if (angle >= 315 || angle < 45) {
			//TOP COLLISION
		}
		else if (angle < 315 && angle >= 225) {
			//LEFT COLLISION
		}
		else if (angle < 225 && angle >= 135) {
			//BOT COLLISION
		}
		else { //angle < 135 && angle >=45
			//RIGHT COLLISION
		}
	}
	
	private double getDotProduct(Vector a, Vector b) {
		return a.getXComponent()*b.getXComponent() + a.getYComponent()*b.getYComponent();
	}
	
	private Vector normalizePosition(Vector entityDiff) {
		double length = Math.sqrt(Math.pow(entityDiff.getXComponent(), 2) + Math.pow(entityDiff.getYComponent(), 2));
		return new Vector(entityDiff.getXComponent()/length, entityDiff.getYComponent()/length);
	}
	
	public void setGravityActive(boolean gravityActive) {
		this.gravityActive = gravityActive;
	}

	public void setCollisionDetectionActive(boolean collisionDetectionActive) {
		this.collisionDetectionActive = collisionDetectionActive;
	}

	public void setFrictionActive(boolean frictionActive) {
		this.frictionActive = frictionActive;
	}
}
