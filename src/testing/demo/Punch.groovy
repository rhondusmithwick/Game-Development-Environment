import model.component.visual.AnimatedSprite
import api.ILevel


ILevel level = universe;
String keyStr = key;

AnimatedSprite animatedSprite = level.getEntitiesWithName("ryu").get(1);
switch (keyStr) {
	case "W": animatedSprite.createAndPlayAnimation("LeftPunch");break;
	case "S": animatedSprite.createAndPlayAnimation("RightPunch");break;
}


