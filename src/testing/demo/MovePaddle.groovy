package testing.demo

import api.IEntity
import api.ILevel
import model.component.character.UserControl
import model.component.movement.Velocity

/**
 * Created by Tom on 4/24/2016.
 */

ILevel level = universe;
String keyStr = key;
String playerStr = player
Set<IEntity> paddles = level.getEntitiesWithComponents(UserControl.class, Velocity.class);

for (IEntity paddle : paddles) {
    if (paddle.getName().equals(playerStr)) {
        Velocity v = paddle.getComponent(Velocity.class);
        switch (keyStr) {
            case "W": moveUp(v); break;
            case "S": moveDown(v); break;
            case "D": stop(v); break;
        }
        switch (keyStr) {
            case "I": moveUp(v); break;
            case "K": moveDown(v); break;
            case "L": stop(v); break;
        }
    }
}

void moveUp(Velocity v) {
    v.setVY(-30);
}

void moveDown(Velocity v) {
    v.setVY(30);
}

void stop(Velocity v) {
    v.setVXY(0, 0);
}
