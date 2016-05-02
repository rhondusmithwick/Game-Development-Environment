package events;

import api.ILevel;
import api.ISerializable;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.Map;

import static utility.ReadFile.readFile;


/**
 * Holds a script for Groovy and executes it with parameters.
 *
 * @author Rhondu Smithwick
 */
public class Action implements ISerializable {
    private final String script;
    private final String scriptPath;
    private final Bindings parameters = new SimpleBindings();

    public Action (String scriptPath) {
        script = readFile(scriptPath);
        this.scriptPath = scriptPath;
    }

    public Action (String scriptPath, Map<String, Object> parameters) {
        this(scriptPath);
        //System.out.println(parameters);
        this.parameters.putAll(parameters);
    }

    public void activate (ScriptEngine engine, ILevel level) {
        parameters.put("universe", level);
        parameters.put("level", level);
        try {
            engine.eval(getScript(), parameters);
        } catch (ScriptException e) {
            //e.printStackTrace();
            e.printStackTrace();
            System.out.println("Error with script:" + scriptPath);
        }
    }

    public String getScript () {
        return script;
    }

    public Bindings getParameters () {
        return parameters;
    }

    public Object putParameter (String key, Object value) {
        return getParameters().put(key, value);
    }

    public Object removeParameter (String key) {
        return getParameters().remove(key);
    }

    @Override
    public String toString () {
        return String.format("Script: %s \n\n, Parameters: %s", script, parameters.toString());
    }

}
