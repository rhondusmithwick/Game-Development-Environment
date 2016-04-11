package groovyScripts

import model.component.movement.Position
import model.entity.Entity

/**
 * Created by rhondusmithwick on 4/9/16.
 * @author Rhondu Smithwick
 */

Entity entity = universe.getEntitiesWithName("Ben").get(0);
Position position = entity.getComponent(Position.class);
position.setX(position.getX() + 50);
