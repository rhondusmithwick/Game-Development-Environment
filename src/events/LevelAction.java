package events;

import api.ILevel;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.Map;

/**
 * Created by cyao42 on 4/24/2016.
 */
public class LevelAction extends Action {

    public LevelAction(String scriptPath) {
        super(scriptPath);
    }

    public LevelAction(String scriptPath, Map<String, String> parameters) {
        super(scriptPath, parameters);
    }

    public void activate(ScriptEngine engine, ILevel level) {
        getParameters().put("level", level);
        try {
            engine.eval(getScript(), getParameters());
        } catch (ScriptException e) {
            //e.printStackTrace();
            System.out.println("Groovy engine could not run script.");
        }
    }
}
