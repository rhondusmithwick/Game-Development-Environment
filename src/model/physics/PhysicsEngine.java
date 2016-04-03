package model.physics;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import api.IEntity;
import api.IEntitySystem;
import api.IPhysicsEngine;
import javafx.scene.shape.Shape;
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
        if (body.hasComponent(Velocity.class)) {
            Velocity v = body.getComponent(Velocity.class);
            v.add(J.getJx(), J.getJy());
        }
    }

    public boolean areCollidingEntities (IEntity e1, IEntity e2) {
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

        return false;
    }

    private boolean areIntersectingIDLists (Collection<String> IDList1,
                                            Collection<String> IDList2) {
        Set<String> IDSet = new HashSet<String>();
        IDSet.addAll(IDList1);
        IDSet.addAll(IDList2);
        return (IDSet.size() < IDList1.size() + IDList2.size());
    }

}
