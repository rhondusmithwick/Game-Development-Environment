// This entire file is part of my masterpiece
// Roxanne Baker

// This class is responsible for handling collisions
// This includes both updating the collision components
// of entities to represent what entities they are colliding with
// and what sides they are colliding with
// It also demonstrates a more extensive usage of functional programming
// and utilizing other important concepts like reflection and enumeration

package model.physics;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.ICollisionSide;
import api.ICollisionVelocityCalculator;
import api.IEntity;
import api.ILevel;
import javafx.geometry.Bounds;
import model.component.physics.Collision;
import voogasalad.util.reflection.Reflection;

public class CollisionHandler {

	private ICollisionVelocityCalculator velocityCalculator;
	
	public CollisionHandler(ICollisionVelocityCalculator velocityCalculator) {
		this.velocityCalculator = velocityCalculator;
	}
	
    public void addCollisionComponents (IEntity firstEntity, IEntity secondEntity) {
        Bounds firstHitBox = getHitBoxForEntity(firstEntity);
        Bounds secondHitBox = getHitBoxForEntity(secondEntity);
        if (firstHitBox.intersects(secondHitBox)) {
        	addEntityIDs(firstEntity, secondEntity);
            addCollisionSide(firstEntity, secondEntity);
            velocityCalculator.changeVelocityAfterCollision(firstEntity, secondEntity);
        }
    }

    public void clearCollisionComponents (List<IEntity> collidableEntities) {
    	collidableEntities.stream().forEach(entity -> entity.getComponent(Collision.class).clearCollidingIDs());
    }

    public void resetCollisionMasks (Collection<IEntity> collidableEntities) {
    	collidableEntities.stream().forEach(entity -> entity.getComponent(Collision.class)
                    	  .setMask(view.utilities.SpriteUtilities.getImageView(entity).getBoundsInParent()));
    }

    private void addEntityIDs (IEntity firstEntity, IEntity secondEntity) {
        firstEntity.getComponent(Collision.class).addCollidingID(secondEntity.getID());
        secondEntity.getComponent(Collision.class).addCollidingID(firstEntity.getID());
    }

    private Bounds getHitBoxForEntity (IEntity entity) {
    	return entity.getComponent(Collision.class).getMask();
    }

    private void addCollisionSide (IEntity e1, IEntity e2) {
        Collision first = e1.getComponent(Collision.class);
        Collision second = e2.getComponent(Collision.class);

        ICollisionSide collisionSide = null;
        double minOverlap = Double.MAX_VALUE;

        for (CollisionTypeEnum collisionType : CollisionTypeEnum.values()) {
            ICollisionSide collision = (ICollisionSide) Reflection.createInstance(collisionType.getType());
            double sideOverlap = collision.getOverlap(first.getMask(), second.getMask());
            if (sideOverlap < minOverlap) {
                minOverlap = sideOverlap;
                collisionSide = collision;
            }
        }
        if(collisionSide!=null) {
        	collisionSide.addCollision(first, second);
        }
    }
    
    public void moveCollidingEntities (ILevel universe) {
        Collection<IEntity> allCollidableEntities = universe.getEntitiesWithComponent(Collision.class);
        allCollidableEntities.stream().forEach(e -> {
            Map<IEntity, String> collidingEntitiesToSides = collidingEntitiesAndSides(e, universe);
            for (IEntity collidingEntity : collidingEntitiesToSides.keySet()) {
                if (velocityCalculator.getMass(e) < velocityCalculator.getMass(collidingEntity)) {
                    moveEntityToSide(e, collidingEntity, collidingEntitiesToSides.get(collidingEntity));
                }
            }
        });
    }

    private void moveEntityToSide (IEntity entityToMove, IEntity entityToStay, String side) {
        for (CollisionTypeEnum collisionType : CollisionTypeEnum.values()) {
            ICollisionSide collision = (ICollisionSide) Reflection.createInstance(collisionType.getType());
            if (collision.getSide().equals(side)) {
                collision.moveEntity(entityToMove, entityToStay);
            }
        }
    }

    private Map<IEntity, String> collidingEntitiesAndSides (IEntity entity, ILevel universe) {
        List<Collision> coll = entity.getComponentList(Collision.class);
        Map<IEntity, String> collidingEntitiesToSides = new HashMap<>();
        for (int i = 0; i < coll.size(); i++) {
            String[] entitiesWithSides = coll.get(i).getCollidingIDsWithSides().split(Collision.COLLISION_SEPARATOR);
            Arrays.stream(entitiesWithSides)
            	  .map(entityAndSide -> entityAndSide.split(Collision.ENTITY_SIDE_SEPARATOR))
            	  .forEach(entityAndSide -> {
            		  IEntity collidingEntity = universe.getEntity(entityAndSide[0]);
            		  if (collidingEntity.getComponent(Collision.class).getMaskID() 
            				  != entity.getComponent(Collision.class).getMaskID()) {
            			  collidingEntitiesToSides.put(collidingEntity, entityAndSide[1]);
            		  }
            });
        }
        return collidingEntitiesToSides;
    }
}