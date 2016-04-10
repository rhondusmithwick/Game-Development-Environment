package events;

import api.IEntitySystem;
import javafx.concurrent.Worker;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by rhondusmithwick on 4/9/16.
 *
 * @author Rhondu Smithwick
 */
public class Action implements Runnable {
    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    private String script;
    private transient IEntitySystem universe;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public Action(IEntitySystem universe, String script) {
        this.script = script;
        this.universe = universe;
    }


    public void activate() {
        executorService.submit(this);
    }

    @Override
    public void run() {
        engine.put("universe", universe);
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void setuniverse(IEntitySystem universe) {
        this.universe = universe;
    }
}
