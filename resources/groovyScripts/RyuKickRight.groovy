package groovyScripts

import model.component.visual.AnimatedSprite;

universe.getEntitySystem().getEntitiesWithName(characterName).get(0).getComponent(AnimatedSprite.class).getAnimation(animationName).play()