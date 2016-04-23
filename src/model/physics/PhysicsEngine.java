package model.physics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import api.IEntity;
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
		if (gravityActive) {
			applyGravity(universe, dt);
		}
		if (collisionDetectionActive) {
			applyCollisions(universe, true);
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
				Velocity.class, Position.class, Collision.class);
		
		entitiesSubjectToGravity.stream().forEach(entity -> {
			System.out.println(entity.getComponent(Collision.class).collidingSide);
			if (!entity.getComponent(Collision.class).collidingSide.equals("bottom")) {
				Position pos = entity.getComponent(Position.class);
				double gravity = entity.getComponent(Gravity.class).getGravity();
				pos.add(0, secondsPassed*secondsPassed*gravity);				
			}
		});
	}

	@Override
	public void applyCollisions(ILevel universe, boolean dynamicsOn) {
		List<IEntity> collidableEntities = new ArrayList<IEntity>(
				universe.getEntitiesWithComponents(Collision.class, ImagePath.class, Mass.class));
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
			entity.getComponent(Collision.class).collidingSide = "";
		}
	}

	public void changeVelocityAfterCollision(IEntity firstEntity, IEntity secondEntity) {
		double restitution = getCollisionRestitution(firstEntity, secondEntity);

		double mass1 = firstEntity.getComponent(Mass.class).getMass();
		Velocity velocity1 = getVelocityComponent(firstEntity);

		double mass2 = secondEntity.getComponent(Mass.class).getMass();
		Velocity velocity2 = getVelocityComponent(secondEntity);

		getSideOfCollision(firstEntity, secondEntity);
		if (isHorizontalCollision(firstEntity, secondEntity)) {
			setVelocityComponent(mass1, mass2, velocity1, velocity2, restitution, (Velocity v) -> v.getVX(),
					(Velocity v, Double val) -> v.setVX(val));			
		}
		if (isVerticalCollision(firstEntity,secondEntity)) {
			setVelocityComponent(mass1, mass2, velocity1, velocity2, restitution, (Velocity v) -> v.getVY(),
					(Velocity v, Double val) -> v.setVY(val));			
		}
	}
	
	private boolean isVerticalCollision(IEntity firstEntity, IEntity secondEntity) {
		if (firstEntity.getComponent(Collision.class).collidingSide == "top" ||
				secondEntity.getComponent(Collision.class).collidingSide == "top") {
			System.out.println("VERTICAL");
			return true;
		}
			return false;
	}
	
	private boolean isHorizontalCollision(IEntity firstEntity, IEntity secondEntity) {
		if (firstEntity.getComponent(Collision.class).collidingSide == "left" ||
				secondEntity.getComponent(Collision.class).collidingSide == "left") {
			System.out.println("HORIZONTAL");
			return true;
		}
			return false;
	}
	
	/**
	 * 
	 * @param firstEntity
	 * @param secondEntity
	 * @return the coefficient of restitution to be used for a collision
	 * between the two entities given
	 */
	private double getCollisionRestitution(IEntity firstEntity, IEntity secondEntity) {
		double firstRestitution = getEntityRestitution(firstEntity);
		double secondRestitution = getEntityRestitution(secondEntity);
		if (firstRestitution < 0 && secondRestitution < 0) {
			return 0.5;
		}
		else if (firstRestitution < 0 || secondRestitution < 0) {
			return Math.max(firstRestitution, secondRestitution);
		}
		else {
			return (firstRestitution + secondRestitution) / 2;
		}		
	}
	
	/**
	 * 
	 * @param entity
	 * @return the coefficient of restitution of the given entity,
	 * or -1 if none exists
	 */
	private double getEntityRestitution(IEntity entity) {
		if (entity.getComponentList(RestitutionCoefficient.class).size() == 0) {
			return -1;
		}
		else {
			return entity.getComponent(RestitutionCoefficient.class).getRestitutionCoefficient();
		}
	}
	
	private Velocity getVelocityComponent(IEntity entity) {
		if (entity.getComponentList(Velocity.class).size() == 0) {
			return new Velocity(0, 0);
		}
		else {
			return entity.getComponent(Velocity.class);
		}
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
	
	private void applyFriction(ILevel universe, double secondsPassed) {
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
	
	private void getSideOfCollision(IEntity firstEntity, IEntity secondEntity) {
		Position firstPos = firstEntity.getComponent(Position.class);
		Position secondPos = secondEntity.getComponent(Position.class);
		
		double firstX = firstEntity.getComponent(Position.class).getX()+firstEntity.getComponent(ImagePath.class).getImageWidth()/2;
		double firstY = firstEntity.getComponent(Position.class).getY()+firstEntity.getComponent(ImagePath.class).getImageHeight()/2;
		double secondX = secondEntity.getComponent(Position.class).getX()+secondEntity.getComponent(ImagePath.class).getImageWidth()/2;
		double secondY = secondEntity.getComponent(Position.class).getY()+secondEntity.getComponent(ImagePath.class).getImageHeight()/2;
		
		Collision firstColl = firstEntity.getComponent(Collision.class);
		Collision secondColl = secondEntity.getComponent(Collision.class);
		
		Vector entityOneToTwo = (new Vector(firstX - secondX,
											firstY - secondY)).normalizePosition();
		Vector referenceVector = new Vector(0, 1);
		double angle = Math.acos(entityOneToTwo.getDotProduct(referenceVector));
		System.out.println(angle);
		if (angle >= 315 || angle < 45) {
			firstColl.collidingSide = "top";
			secondColl.collidingSide = "bottom";		
		}
		else if (angle < 315 && angle >= 225) {
			//LEFT COLLISION
			firstColl.collidingSide = "left";
			secondColl.collidingSide = "right";	
		}
		else if (angle < 225 && angle >= 135) {
			//BOT COLLISION
			firstColl.collidingSide = "bottom";
			secondColl.collidingSide = "top";	
		}
		else { //angle < 135 && angle >=45
			//RIGHT COLLISION
			firstColl.collidingSide = "right";
			secondColl.collidingSide = "left";	
		}
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
