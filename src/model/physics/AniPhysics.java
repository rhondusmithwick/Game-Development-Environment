package model.physics;

import java.util.HashMap;
import java.util.Map;

import api.IEntity;
import api.ILevel;
import api.IPhysicsEngine;

public class AniPhysics implements IPhysicsEngine {
	
	private Map<String, Vector> globalForces;

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
	public boolean applyImpulse(IEntity body, Vector J) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addGlobalForce(String forceName, Vector force) {
		globalForces.put(forceName, force);
	}
	
	public Vector getGlobalForce(String forceName) {
		return globalForces.get(forceName);
	}
	
	public void removeGlobalForce(String forceName) {
		globalForces.remove(forceName);
	}
	
	private void calculateForces(IEntity entity) {
		
	}
	
	private Vector netGlobalForces() {
		Vector netVector = globalForces.values().stream()
			.reduce((a,b)->a.add(b))
			.get();
		return netVector;
	}
	
	public void update() {
		
	}
	
	public static void main(String[] args){
		AniPhysics ani = new AniPhysics();
		ani.addGlobalForce("f1", new Vector(1, 2));
		ani.addGlobalForce("f2", new Vector(3, 4));
		System.out.println(ani.netGlobalForces().getXComponent());
		System.out.println(ani.netGlobalForces().getYComponent());
	}

}
