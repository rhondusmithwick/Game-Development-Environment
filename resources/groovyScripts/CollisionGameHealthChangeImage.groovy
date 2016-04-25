package groovyScripts

/**
 * Created by cyao42 on 4/23/2016.
 */
import model.component.character.Health
import model.component.movement.Position
import model.component.visual.ImagePath

charEntity = universe.getEntitiesWithName("Anolyn").get(0);
position = charEntity.getComponent(Position.class);
health = charEntity.getComponent(Health.class);
image = charEntity.getComponent(ImagePath.class);
if (health.getHealth() <= 0 ) {
    String IMAGE_PATH = "resources/images/jasmine.png";
    charEntity.getComponent(ImagePath.class).setImagePath(IMAGE_PATH);
    charEntity.addComponent(new ImagePath(IMAGE_PATH));
}