package groovyScripts

import model.component.character.Health
import model.component.movement.Position
import model.component.visual.ImagePath

/**
 * Created by cyao42 on 4/22/2016.
 */

charEntity = universe.getEntitiesWithName("Blastoise").get(0);
collision = charEntity.getComponent(Collision.class);
if (!collision.getCollidingIDs.equals("")) {
    System.out.println("colliding with something! hi from groovy");
    String IMAGE_PATH = "resources/images/jasmine.png";
    charEntity.getComponent(ImagePath.class).setImagePath(IMAGE_PATH);
}