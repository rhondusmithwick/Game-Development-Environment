package model.entity;

import api.ICollisionVelocityCalculator;
import api.IEntitySystem;
import api.IEventSystem;
import api.IGameScript;
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import events.EventSystem;
import groovy.lang.GroovyShell;
import javafx.scene.Scene;
import model.physics.PhysicsEngine;
import model.physics.RealisticVelocityCalculator;
import view.enums.DefaultStrings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Implementation of a Level. This implementation is focused on the IDs. It
 * spawns entities based on the next available ID and adds them to the system.
 *
 * @author Tom Wu
 */
public class Level implements ILevel {

    private final IEntitySystem universe = new EntitySystem();
    private Map<String, String> metadata = Maps.newLinkedHashMap();
    private final IEventSystem eventSystem = new EventSystem(this);
    private final ICollisionVelocityCalculator velocityCalculator = new RealisticVelocityCalculator();
    private final IPhysicsEngine physics = new PhysicsEngine(velocityCalculator);
    private String eventSystemPath;
    private transient ResourceBundle myResources;
    //	private transient ResourceBundle scriptLocs = ResourceBundle.getBundle(DefaultStrings.SCRIPTS_LOC.getDefault());
    private transient List<IGameScript> gameScripts = Lists.newArrayList();
    private transient boolean levelOverBool = false;
    private transient String nextLevelPath = "";

    public Level () {
        this("");
    }

    public Level (String name) {
        universe.setName(name);
        myResources = ResourceBundle.getBundle(DefaultStrings.LANG_LOC.getDefault() + DefaultStrings.DEFAULT_LANGUAGE.getDefault());
    }

    @Override
    public String getName () {
        return universe.getName();
    }

    @Override
    public void setName (String name) {
        universe.setName(name);
    }

    @Override
    public Map<String, String> getMetadata () {
        return this.metadata;
    }

    @Override
    public void setMetadata (Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public void addMetadata (String key, String value) {
        this.metadata.put(key, value);
    }

    @Override
    public String init (GroovyShell shell, ISystemManager game, Scene scene) {
        setOnInput(scene);
        gameScripts = new ArrayList<>();
        String returnMessage = "Level initializing...\n";
        String key = myResources.getString("script"); // TODO: don't hard-code
        //System.out.println(this.metadata.keySet());
        if (this.metadata.containsKey(key)) {
            String value = this.metadata.get(key);
            String[] scripts = value.split(",");
            for (String script : scripts) {
                try {
                    IGameScript gameScript = (IGameScript) Class.forName(script).getConstructor()
                            .newInstance(); // TODO: scriptLocs.getString(script)
                    gameScript.init(shell, game);
                    gameScripts.add(gameScript);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException | NoSuchMethodException | SecurityException
                        | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            returnMessage += "No scripts\n";
        }
        return (returnMessage+"Level initialized.\n");
    }

    @Override
    public void update (double dt) {
        getEventSystem().updateInputs(dt);
       // gameScripts.stream().forEach(gs -> gs.update(dt));
       getPhysicsEngine().update(this, dt); // TODO: remove
    }

    @Override
    public IEventSystem getEventSystem () {
        return this.eventSystem;
    }

    @Override
    public IEntitySystem getEntitySystem () {
        return this.universe;
    }

    @Override
    public IPhysicsEngine getPhysicsEngine () {
        return this.physics;
    }

    public String getEventSystemPath () {
        return eventSystemPath;
    }

    @Override
    public void setEventSystemPath (String eventSystemPath) {
        this.eventSystemPath = eventSystemPath;
    }

    @Override
    public void setOnInput (Scene scene) {
        getEventSystem().setOnInput(scene);
    }

    @Override
    public void setLevelOverAndLoadNextLevel (String nextLevelPath) {
        levelOverBool = true;
        this.nextLevelPath = nextLevelPath;
    }

    @Override
    public boolean checkIfLevelOver () {
        return levelOverBool;
    }

    @Override
    public String getNextLevelPath () {
        return nextLevelPath;
    }

    private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        myResources = ResourceBundle.getBundle(DefaultStrings.LANG_LOC.getDefault() + DefaultStrings.DEFAULT_LANGUAGE.getDefault());
        eventSystem.setLevel(this);
        levelOverBool = false;
        nextLevelPath = "";
    }

}
