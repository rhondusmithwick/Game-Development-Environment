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
public class Action {

    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    private String script;

    public Action(String script) {
        this.script = script;
    }


    public void activate(IEntitySystem universe) {
        engine.put("universe", universe);
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

}
