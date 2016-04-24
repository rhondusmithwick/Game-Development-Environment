package groovyScripts

import model.component.character.Health
import model.component.movement.Position
import model.component.physics.Collision
import model.component.visual.ImagePath

/**
 * Created by cyao42 on 4/22/2016.
 */

charEntity = universe.getEntitiesWithName("Anolyn").get(0);
collision = charEntity.getComponent(Collision.class);
System.out.println("Colliding ID: " + collision.getCollidingIDs());
if (!collision.getCollidingIDs.equals("")) {
    System.out.println("colliding with something! hi from groovy");

    health = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Health.class);
    health.setHealth(0);
//    String IMAGE_PATH = "resources/images/jasmine.png";
//    charEntity.getComponent(ImagePath.class).setImagePath(IMAGE_PATH);
}