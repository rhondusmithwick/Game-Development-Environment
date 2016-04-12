package animation;

import api.IEntity;
import api.IEntitySystem;

public interface IAnimationEngine {
	IEntitySystem update(IEntitySystem system, double dt);

	void interpolate(IEntity entity, double dt);
}
