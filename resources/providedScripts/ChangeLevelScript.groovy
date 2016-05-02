package providedScripts

import groovy.transform.BaseScript
import groovy.transform.Field
import model.component.movement.Velocity

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Changes the level
 * @author Anirudh Jonnavithula
 */

// Parameter: levelPath: path of next level
@Field String levelPathField = containsVariable("levelPath") ? getString("levelPath") : "";

universe.setLevelOverAndLoadNextLevel(levelPathField);