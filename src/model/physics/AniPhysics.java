package model.physics;

import java.util.HashMap;
import java.util.Map;

import api.IEntity;
import api.ILevel;
import api.IPhysicsEngine;
import javafx.geometry.Point2D;

public class AniPhysics implements IPhysicsEngine {
	
	private Map<String, Point2D> globalForces;

	public AniPhysics() {
		globalForces = new HashMap<>();
	}
	
	@Override
	public void update(ILevel level, double dt) {
		level.getEntitySystem().getAllEntities().stream().forEach(e-> {
			calculateForces(e);
		});
			
		
	}

	@Override
	public void applyCollisions(ILevel universe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean applyImpulse(IEntity body, Point2D J) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addGlobalForce(String forceName, Point2D force) {
		globalForces.put(forceName, force);
	}
	
	public Point2D getGlobalForce(String forceName) {
		return globalForces.get(forceName);
	}
	
	public void removeGlobalForce(String forceName) {
		globalForces.remove(forceName);
	}
	
	private void calculateForces(IEntity entity) {
		
	}
	
	private Point2D netGlobalForces() {
		Point2D netVector = globalForces.values().stream()
			.reduce((a,b)->a.add(b))
			.get();
		return netVector;
	}
	
	public void update() {
		
	}
	
	public static void main(String[] args){
		AniPhysics ani = new AniPhysics();
		ani.addGlobalForce("f1", new Point2D(1, 2));
		ani.addGlobalForce("f2", new Point2D(3, 4));
		System.out.println(ani.netGlobalForces().getX());
		System.out.println(ani.netGlobalForces().getY());
	}

}
