package testing.demo;

import api.IEventSystem;
import api.IGameScript;
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;
import groovy.lang.GroovyShell;

public class AudioTest implements IGameScript {
	
	private ISystemManager game;
	private ILevel universe;
    private IEventSystem events;

	@Override
	public void init(GroovyShell shell, ISystemManager game) {
		this.game = game;
		this.universe = game.getLevel();
        this.events = universe.getEventSystem();

		
	}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}

}
