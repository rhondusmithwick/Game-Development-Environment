package model.physics;

import api.ICollisionSide;
import api.ICollisionVelocityCalculator;
import api.IEntity;
import api.ILevel;
import api.IPhysicsEngine;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;
import model.component.physics.Gravity;
import model.component.physics.Mass;
import model.component.visual.Sprite;
import voogasalad.util.reflection.Reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of the physics engine
 *
 * @author Tom Wu and Roxanne Baker
 */
public class PhysicsEngine implements IPhysicsEngine {

    ICollisionVelocityCalculator velocityCalculator;
    private boolean gravityActive = true;
    private boolean collisionDetectionActive = true;

    public PhysicsEngine (ICollisionVelocityCalculator velocityCalculator) {
        this.velocityCalculator = velocityCalculator;
    }

    public PhysicsEngine (ICollisionVelocityCalculator velocityCalculator, boolean gravityActive, boolean collisionDetectionActive) {
        this(velocityCalculator);
        this.collisionDetectionActive = collisionDetectionActive;
        this.velocityCalculator = velocityCalculator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update (ILevel universe, double dt) {
        Collection<IEntity> entities = universe.getEntitiesWithComponents(Position.class, Sprite.class);
        updateImageViews(entities);
        if (gravityActive) {
            applyGravity(universe, dt);
        }
        if (collisionDetectionActive) {
            applyCollisions(universe);
        }
        Collection<IEntity> dynamicEntities = universe.getEntitiesWithComponents(Position.class, Velocity.class);
        dynamicEntities.stream().forEach(p -> {
            Position pos = p.getComponent(Position.class);
            Velocity velocity = p.getComponent(Velocity.class);
            double dx = dt * velocity.getVX();
            double dy = dt * velocity.getVY();
            pos.add(dx, dy);
        });
        updateImageViews(entities);
        resetCollisionMasks(universe.getEntitiesWithComponent(Collision.class));
        moveCollidingEntities(universe);
    }

    private void updateImageViews (Collection<IEntity> entities) {
        entities.stream().forEach(p -> {
            Position pos = p.getComponent(Position.class);
            ImageView imageView = p.getComponent(Sprite.class).getImageView();
            imageView.setTranslateX(pos.getX());
            imageView.setTranslateY(pos.getY());
        });
    }

    public void moveCollidingEntities (ILevel universe) {
        Collection<IEntity> allCollidableEntities = universe.getEntitiesWithComponent(Collision.class);
        allCollidableEntities.stream().forEach(e -> {
            Map<IEntity, String> collidingEntitiesToSides = collidingEntitiesAndSides(e, universe);
            collidingEntitiesToSides.keySet().stream().filter(collidingEntity -> velocityCalculator.getMass(e) < velocityCalculator.getMass(collidingEntity)).forEach(collidingEntity -> {
                moveEntityToSide(e, collidingEntity, collidingEntitiesToSides.get(collidingEntity));
            });
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

    public Map<IEntity, String> collidingEntitiesAndSides (IEntity entity, ILevel universe) {
        List<Collision> coll = entity.getComponentList(Collision.class);
        Map<IEntity, String> collidingEntitiesToSides = new HashMap<>();
        for (Collision aColl : coll) {
            String[] entitiesWithSides = aColl.getCollidingIDsWithSides().split(Collision.ID_SEPARATOR);
            for (int j = 1; j < entitiesWithSides.length; j++) {
                String[] entityAndSide = entitiesWithSides[j].split(Collision.SIDE_SEPARATOR);
                IEntity entityCollidingWith = universe.getEntity(entityAndSide[0]);
                if (entityCollidingWith.getComponent(Collision.class).getMaskID() !=
                        entity.getComponent(Collision.class).getMaskID()) {
                    collidingEntitiesToSides.put(entityCollidingWith, entityAndSide[1]);
                }
            }
        }
        return collidingEntitiesToSides;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean applyImpulse (IEntity body, Point2D impulse) {
        if (body.hasComponents(Mass.class, Velocity.class)) {
            Velocity v = body.getComponent(Velocity.class);
            double m = body.getComponent(Mass.class).getMass();
            v.add(impulse.getX() / m, impulse.getY() / m);
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public void applyGravity (ILevel universe, double secondsPassed) {
        Collection<IEntity> entitiesSubjectToGravity = universe.getEntitiesWithComponents(Gravity.class,
                Velocity.class);

        entitiesSubjectToGravity.stream().forEach(entity -> {
            Gravity acceleration = entity.getComponent(Gravity.class);
            Velocity velocity = entity.getComponent(Velocity.class);
            velocity.add(acceleration.getGravityX(), acceleration.getGravityY());
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public void applyCollisions (ILevel universe) {
        List<IEntity> collidableEntities = new ArrayList<>(
                universe.getEntitiesWithComponents(Collision.class, Sprite.class));// ,
        // Mass.class));
        clearCollisionComponents(collidableEntities);
        for (int i = 0; i < collidableEntities.size(); i++) {
            for (int j = i + 1; j < collidableEntities.size(); j++) {
                addCollisionComponents(collidableEntities.get(i), collidableEntities.get(j));
            }
        }
    }

    private void addCollisionComponents (IEntity firstEntity, IEntity secondEntity) {
        for (Bounds firstHitBox : getHitBoxesForEntity(firstEntity)) {
            getHitBoxesForEntity(secondEntity).stream().filter(secondHitBox -> firstHitBox.intersects(secondHitBox)).forEach(secondHitBox -> {
                addEntityIDs(firstEntity, secondEntity);
                addCollisionSide(firstEntity, secondEntity);
                velocityCalculator.changeVelocityAfterCollision(firstEntity, secondEntity);
            });
        }
    }

    private void clearCollisionComponents (List<IEntity> collidableEntities) {
        for (IEntity entity : collidableEntities) {
            entity.getComponent(Collision.class).clearCollidingIDs();
            entity.getComponent(Collision.class)
                    .setMask(entity.getComponent(Sprite.class).getImageView().getBoundsInParent());
        }
    }

    private void resetCollisionMasks (Collection<IEntity> collidableEntities) {
        for (IEntity entity : collidableEntities) {
            entity.getComponent(Collision.class)
                    .setMask(entity.getComponent(Sprite.class)
                            .getImageView().getBoundsInParent());
        }
    }

    private void addEntityIDs (IEntity firstEntity, IEntity secondEntity) {
        firstEntity.getComponent(Collision.class).addCollidingID(secondEntity.getID());
        secondEntity.getComponent(Collision.class).addCollidingID(firstEntity.getID());
    }

    private List<Bounds> getHitBoxesForEntity (IEntity entity) {
        List<Collision> collisionComponents = entity.getComponentList(Collision.class);
        List<Bounds> hitBoxes = collisionComponents.stream().map(Collision::getMask).collect(Collectors.toList());
        return hitBoxes;
    }

    private void addCollisionSide (IEntity e1, IEntity e2) {
        ICollisionSide collisionSide = null;
        double minOverlap = Double.MAX_VALUE;

        for (CollisionTypeEnum collisionType : CollisionTypeEnum.values()) {
            ICollisionSide collision = (ICollisionSide) Reflection.createInstance(collisionType.getType());
            double sideOverlap = collision.getOverlap(getBounds(e1), getBounds(e2));
            if (sideOverlap < minOverlap) {
                minOverlap = sideOverlap;
                collisionSide = collision;
            }
        }
        if (collisionSide == null) {
            return;
        }
        collisionSide.addCollision(e1.getComponent(Collision.class), e2.getComponent(Collision.class));
    }

    private Bounds getBounds (IEntity entity) {
        return entity.getComponent(Collision.class).getMask();
    }

    public void setGravityActive (boolean gravityActive) {
        this.gravityActive = gravityActive;
    }

    public void setCollisionDetectionActive (boolean collisionDetectionActive) {
        this.collisionDetectionActive = collisionDetectionActive;
    }
}