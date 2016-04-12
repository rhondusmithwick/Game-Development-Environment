package events;

import api.IEntitySystem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rhondusmithwick on 4/9/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Action implements ChangeListener{

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    private String script;
    private final Map<String, Object> parameters = new HashMap<>();


    public Action(String script) {
        setScript(script);
    }

    public Action(String script, Map<String,Object> parameters) {
        this(script);
        this.parameters.putAll(parameters);
    }

    public void activate() {
        setUp();
        parameters.entrySet().stream().forEach(e -> engine.put(e.getKey(), e.getValue()));
        try {
            getEngine().eval(getScript());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    protected abstract void setUp();

    public ScriptEngine getEngine() {
        return engine;
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

    public Object removeParameter(Object key) {
        return getParameters().remove(key);
    }

    @Override
	public void changed(ObservableValue arg0, Object arg1, Object arg2) {
		System.out.println("IT WORKS YA DOOF");
	}
}
