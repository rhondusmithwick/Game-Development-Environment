package groovyScripts

import model.component.character.Health
import model.component.movement.Position
import model.component.visual.ImagePath

/**
 * Created by cyao42 on 4/22/2016.
 */

charEntity = universe.getEntitiesWithName("Anolyn").get(0);
position = charEntity.getComponent(Position.class);
health = charEntity.getComponent(Health.class);
image = charEntity.getComponent(ImagePath.class);
if (health.getHealth() <= 0 ) {
    String IMAGE_PATH = "resources/images/blastoise.png";
    charEntity.addComponent(new ImagePath(IMAGE_PATH));

}