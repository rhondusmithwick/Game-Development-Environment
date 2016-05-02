package testing.demo

import api.IEntity
import api.ILevel
import model.component.character.UserControl
import model.component.movement.Velocity

/**
 * Created by Tom on 5/02/2016.
 */

ILevel level = universe;
Set<IEntity> paddles = level.getEntitiesWithComponents(UserControl.class, Velocity.class);

for (IEntity paddle : paddles) {
    Velocity v = paddle.getComponent(Velocity.class);
    v.setVX(0);
    println("restricting X"); // TODO: remove
    println();
}
