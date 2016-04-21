package events;

import api.ILevel;
import api.ISerializable;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;


/**
 * Created by rhondusmithwick on 4/9/16.
 *
 * @author Rhondu Smithwick
 */
public class Action implements ISerializable {
    private final String script;
    private final Bindings parameters;
    private transient ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");

    public Action(String scriptPath) {
        script = getScriptFromPath(scriptPath);
        parameters = new SimpleBindings();
    }

    public Action(String scriptPath, Map<String, Object> parameters) {
        this(scriptPath);
        this.parameters.putAll(parameters);
    }

    public void activate(ILevel universe) {
        parameters.put("universe", universe);
        try {
            engine.eval(getScript(), parameters);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public String getScript() {
        return script;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Object putParameter(String key, Object value) {
        return getParameters().put(key, value);
    }

    public Object removeParameter(String key) {
        return getParameters().remove(key);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        engine = new ScriptEngineManager().getEngineByName("groovy");
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
}
