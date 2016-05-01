package groovyScripts

/**
 * Created by cyao42 on 4/23/2016.
 */
import model.component.character.Health
import model.component.movement.Position
import model.component.visual.Sprite

charEntity = universe.getEntity(entityID);
position = charEntity.getComponent(Position.class);
health = charEntity.getComponent(Health.class);
image = charEntity.getComponent(Sprite.class);
if (health.getHealth() <= 0 ) {
    String IMAGE_PATH = "resources/images/jasmine.png";
    charEntity.getComponent(Sprite.class).setImagePath(IMAGE_PATH);
    charEntity.addComponent(new Sprite(IMAGE_PATH));
}
else {
	String IMAGE_PATH = "resources/images/blastoise.png";
	charEntity.getComponent(Sprite.class).setImagePath(IMAGE_PATH);
	charEntity.addComponent(new Sprite(IMAGE_PATH));
}