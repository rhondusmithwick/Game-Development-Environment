package events;

import api.ILevel;
import api.ISerializable;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.io.File;
import java.io.IOException;
import java.util.Map;


/**
 * Created by rhondusmithwick on 4/9/16.
 *
 * @author Rhondu Smithwick
 */
public class Action implements ISerializable {
    private final String script;
    private final String scriptPath;
    private final Bindings parameters;

    public Action(String scriptPath) {
        script = getScriptFromPath(scriptPath);
        parameters = new SimpleBindings();
        this.scriptPath = scriptPath;
    }

    public Action(String scriptPath, Map<String, Object> parameters) {
        this(scriptPath);
        this.parameters.putAll(parameters);
    }

    public void activate(ScriptEngine engine, ILevel level) {
        parameters.put("universe", level);
        parameters.put("level", level);
        try {
            engine.eval(getScript(), parameters);
        } catch (ScriptException e) {
            e.printStackTrace();
            System.out.println("Error with script:" + scriptPath);
        }
    }

    public String getScript() {
        return script;
    }

    protected Bindings getParameters() {
        return parameters;
    }

    public Object putParameter(String key, Object value) {
        return getParameters().put(key, value);
    }

    public Object removeParameter(String key) {
        return getParameters().remove(key);
    }

    private String getScriptFromPath(String scriptPath) {
        String script = "";
        try {
            script = Files.toString(new File(scriptPath), Charsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Groovy script not found at " + scriptPath);
        }
        return script;
    }
    
    public String toString() {
    	return this.scriptPath;
    }
}
