package model.physics;

import java.util.function.BiConsumer;
import java.util.function.Function;

import api.ICollisionVelocityCalculator;
import api.IEntity;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Mass;
import model.component.physics.RestitutionCoefficient;

public class RealisticVelocityCalculator implements ICollisionVelocityCalculator {

	
	public RealisticVelocityCalculator() {
	}
	
	public void changeVelocityAfterCollision(IEntity firstEntity, IEntity secondEntity) {
		double restitution = getCollisionRestitution(firstEntity, secondEntity);
		double m1 = getMass(firstEntity);
		double m2 = getMass(secondEntity);
		Velocity velocity1 = getVelocityComponent(firstEntity);
		Velocity velocity2 = getVelocityComponent(secondEntity);

		if (collisionIsHorizontal(firstEntity)) {
			changeVelocity(m1, m2, velocity1, velocity2, restitution,
					(Velocity v, Double val) -> v.setVX(val),
					(Velocity v, Double val) -> v.setVY(-val));
		}
		else if (collisionIsVertical(firstEntity)) {
			changeVelocity(m1, m2, velocity1, velocity2, restitution,
					(Velocity v, Double val) -> v.setVX(-val),
					(Velocity v, Double val) -> v.setVY(val));

		}
	}
	
	public double getMass(IEntity entity) {
		Mass mass = entity.getComponent(Mass.class);
		if (mass != null) {
			return mass.getMass();
		}
		else {
			return Double.MAX_VALUE;
		}
	}
	
	private void changeVelocity(double m1, double m2, Velocity velocity1,
			Velocity velocity2, double restitution,
			BiConsumer<Velocity, Double> setXVelocity,
			BiConsumer<Velocity, Double> setYVelocity) {
		setVelocityComponent(m1, m2, velocity1, velocity2, restitution,
				(Velocity v) -> v.getVX(), setXVelocity);
		setVelocityComponent(m1, m2, velocity1, velocity2, restitution,
				(Velocity v) -> v.getVY(), setYVelocity);	
	}

	 private boolean collisionIsFromSide(IEntity entity, String side) {
	 	return entity.getComponent(Collision.class).getCollidingIDsWithSides().endsWith(side);
	 }

	 private boolean collisionIsHorizontal(IEntity entity) {
		 return collisionIsFromSide(entity, Collision.LEFT) ||
		 collisionIsFromSide(entity, Collision.RIGHT);
	 }

	 private boolean collisionIsVertical(IEntity entity) {
		 return collisionIsFromSide(entity, Collision.TOP) ||
		 collisionIsFromSide(entity, Collision.BOTTOM);
	 }

	private double getCollisionRestitution(IEntity firstEntity, IEntity secondEntity) {
		return (getEntityRestitution(firstEntity) + getEntityRestitution(secondEntity)) / 2;
	}

	private double getEntityRestitution(IEntity entity) {
		if (entity.getComponentList(RestitutionCoefficient.class).size() == 0) {
			return 0.0;
		} else {
			return entity.getComponent(RestitutionCoefficient.class).getRestitutionCoefficient();
		}
	}

	private Velocity getVelocityComponent(IEntity entity) {
		if (entity.getComponentList(Velocity.class).size() == 0) {
			return new Velocity(0, 0);
		} else {
			return entity.getComponent(Velocity.class);
		}
	}

	 private void setVelocityComponent(double mass1, double mass2, Velocity velocity1,
			 Velocity velocity2, double restitution, 
			 Function<Velocity, Double> getCoordinate, BiConsumer<Velocity, Double> setVelocity) {
		 double initialVelocity1 = getCoordinate.apply(velocity1);
		 double initialVelocity2 = getCoordinate.apply(velocity2);

		 double velocityBeforeRestitution = getVelocityBeforeRestitution(mass1,
		 mass2, initialVelocity1, initialVelocity2);
		 double finalVelocity1 = velocityBeforeRestitution
		 + ((mass2 * restitution * (initialVelocity2 - initialVelocity1)) /
			(mass1 + mass2));
		 double finalVelocity2 = velocityBeforeRestitution
		 + ((mass1 * restitution * (initialVelocity1 - initialVelocity2)) / 
			(mass1 + mass2));
		 setVelocity.accept(velocity1, finalVelocity1);
		 setVelocity.accept(velocity2, finalVelocity2);
	 }

	 private double getVelocityBeforeRestitution(double mass1, double mass2,
	 double velocity1, double velocity2) {
	 return ((mass1 * velocity1) + (mass2 * velocity2)) / (mass1 + mass2);
	 }
	
}
