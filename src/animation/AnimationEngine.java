package animation;

import java.util.Collection;

import api.IEntity;
import api.IEntitySystem;
import model.component.movement.Position;
import model.component.visual.ImagePath;

public class AnimationEngine implements IAnimationEngine {

	@Override
	public IEntitySystem update(IEntitySystem system, double dt) {
		Collection<IEntity> animatedEntities = system.getEntitiesWithComponents(Position.class, ImagePath.class);
		animatedEntities.stream().forEach(e -> this.interpolate(e, dt));
		return system;
	}

	@Override
	public void interpolate(IEntity entity, double dt) {
		if (entity.hasComponent(ImagePath.class)) {
			ImagePath animation = entity.getComponent(ImagePath.class);
			animation.updateTime(dt);
			double elapsedTime = animation.getElapsedTime();
			if (elapsedTime < animation.getDuration()) {
				double timeSinceLastFrame = animation.getTimeSinceLastFrame();
				if (timeSinceLastFrame > animation.getFrameDuration()) {
					animation.incrementFrameIndex();
					animation.resetTimeSinceLastFrame();
				}
			}
			System.out.println("index=" + animation.getFrameIndex());
			System.out.println(animation.getViewport());
		}
	}

}
