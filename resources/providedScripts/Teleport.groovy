package providedScripts;

import api.IEntity
import model.component.movement.Position

/**
 * Created by rhondusmithwick on 4/20/16.
 * @author Rhondu Smithwick
 */
IEntity entity = universe.getEntity(entityID);

Position position = entity.getComponent(Position.class);

double newX = position.getX() + dx;
double newY = position.getY() + dy;

position.setXY(newX, newY);
