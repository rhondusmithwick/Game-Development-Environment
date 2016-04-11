package events;

import api.IEntitySystem;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by rhondusmithwick on 4/9/16.
 *
 * @author Rhondu Smithwick
 */
public abstract class Action {

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    private String script;

    public Action(String script) {
        this.script = script;
    }

    public void activate() {
        setUp();
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

}
