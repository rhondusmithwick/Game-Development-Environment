package api;

public interface IPhysicsEngine {
    IEntitySystem update(IEntitySystem universe, double dt);
}
