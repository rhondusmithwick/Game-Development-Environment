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

@Field String nextLevel = containsVariable("nextLevel") ? getVariable("nextLevel") : "";

if (!nextLevel.equals("")) {
    universe.setLevelOverAndLoadNextLevel(nextLevel);
}





