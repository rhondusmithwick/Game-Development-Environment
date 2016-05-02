package providedScripts

import groovy.transform.BaseScript

/**
 * Created by cyao42 on 5/1/2016.
 */
@BaseScript ScriptHelpers ScriptHelpers

/**
 * Removes the entity
 * @author Tom
 */


def remove = { entity ->
    universe.removeEntity(entityID)
}

workOnEntities(remove);