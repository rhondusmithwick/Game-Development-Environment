package groovyScripts

/**
 * Created by ajonnav on 4/16/16.
 * @author Anirudh Jonnavithula
 */

import model.component.movement.Velocity;
import model.component.movement.Position;
import model.component.character.Health;
import model.physics.PhysicsEngine;


velocity = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Velocity.class);
pos = universe.getEntitiesWithName("Anolyn").get(0).getComponent(Position.class);
if(pos.getY()>200) {
	velocity.setVXY(velocity.getVX(), 0);
	pos.setX(pos.getX());
	pos.setY(200);
}