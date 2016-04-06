package api;

import model.entity.IEntitySystem;

public interface IPhysicsEngine {
    IEntitySystem update(IEntitySystem universe, double dt);
}
