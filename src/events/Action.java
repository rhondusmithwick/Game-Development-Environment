package events;

import api.IEntitySystem;
import api.ISerializable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
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

    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
    private String script; 
    private String scriptPath;
    private final Map<String, Object> parameters = new HashMap<>();

    public Action(String scriptPath) {
        setScriptPath(scriptPath);
    }

    public Action(String scriptPath, Map<String, Object> parameters) {
        this(scriptPath);
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
    
    public void setScriptPath(String scriptPath) {
    	this.scriptPath = scriptPath;
		
    }
    
    public String getScriptPath() {
    	return scriptPath;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScript() {
    	if(script==null) {
    		setScriptFromPath(scriptPath);
    	}
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
    
    private void setScriptFromPath(String scriptPath) {
    	String scr = null;
		try {
			scr = Files.toString(new File(scriptPath), Charsets.UTF_8);
			setScript(scr);
		} catch (IOException e) {
			System.out.println("Groovy script not found at " + scriptPath);
		}
    }
}
