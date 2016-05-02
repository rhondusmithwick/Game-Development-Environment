package providedScripts

/**
 * Created by cyao42 on 5/1/2016.
 */
import groovy.transform.BaseScript
import groovy.transform.Field

@BaseScript ScriptHelpers ScriptHelpers

/**
 * Level changing generic script
 * @author Carolyn Yao
 */

@Field String nextLevelPathField = containsVariable("nextLevelPath") ? getVariable("nextLevelPath") : "";

if (!nextLevelPathField.equals("")) {
    universe.setLevelOverAndLoadNextLevel(nextLevelPathField);
}

