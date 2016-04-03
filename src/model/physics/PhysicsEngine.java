package model.physics;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import api.IEntity;
import api.IEntitySystem;
import api.IPhysicsEngine;
import model.component.movement.Position;
import model.component.movement.Velocity;
import model.component.physics.Collision;


public class PhysicsEngine implements IPhysicsEngine {
    IEntitySystem settings;

    public PhysicsEngine (IEntitySystem settings) {
        this.settings = settings;
    }

    @Override
    public IEntitySystem update (IEntitySystem universe, double dt) {
        Collection<IEntity> dynamicEntities = universe.getAllEntities().stream()
                .filter(p -> p.hasComponents(Position.class, Velocity.class))
                .collect(Collectors.toSet());
        dynamicEntities.stream().forEach(p -> {
            Position pos = p.getComponent(Position.class);
            Velocity velocity = p.getComponent(Velocity.class);
            double dx = dt * velocity.getVX();
            double dy = dt * velocity.getVY();
            pos.add(dx, dy);
        });
        return universe;
    }

    public void applyImpulse (IEntity body, Impulse J) {
        Velocity v = body.getComponent(Velocity.class);
        v.add(J.getJx(), J.getJy());
    }

    public boolean areColliding (IEntity e1, IEntity e2) {
        List<Collision> collisionComponents = e1.getComponentList(Collision.class);
    }

}
