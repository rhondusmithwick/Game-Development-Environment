package events;

import api.IEntitySystem;
import api.ISerializable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/9/16.
 *
 * @author Rhondu Smithwick
 */
public class Action implements ISerializable{

    private transient ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    private String script;
    private final Map<String, Object> parameters = new HashMap<>();

    public Action(String script) {
        setScript(script);
    }

    public Action(String script, Map<String, Object> parameters) {
        this(script);
        this.parameters.putAll(parameters);
    }

    public void activate(IEntitySystem universe) {
        engine.put("universe", universe);
        parameters.entrySet().stream().forEach(e -> engine.put(e.getKey(), e.getValue()));
        try {
            engine.eval(getScript());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void setScript(String script) {
        this.script = script;
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

}
