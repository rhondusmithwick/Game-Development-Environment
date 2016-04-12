package groovyScripts

/**
 * Created by rhondusmithwick on 4/8/16.
 * @author Rhondu Smithwick
 */

import model.component.movement.Position;

position = entity.getComponent(Position.class);

def moved = $1 as double;

position.setX(position.getX() + moved);