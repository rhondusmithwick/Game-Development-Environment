import events.Action
import events.KeyTrigger
import groovy.lang.GroovyShell;
import model.physics.PhysicsEngine
import api.IEventSystem;
import api.IGameScript
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;

public class punch implements IGameScript{
	
	private ISystemManager game;
	private ILevel universe;
	private final IPhysicsEngine physics = new PhysicsEngine();
	private IEventSystem events;
	@Override
	public void init(GroovyShell shell, ISystemManager game) {
		this.game = game;
		this.universe = game.getEntitySystem();
		this.events = universe.getEventSystem()
		shell.setVariable("punch");
		initKeyInputs();
		initSprites();
		}

	@Override
	public void update(double dt) {
		// TODO Auto-generated method stub
		
	}
	private void initKeyInputs() {

		Map<String, Object> sKey = new HashMap<>();
		sKey.put("key", "S");

		events.registerEvent(new KeyTrigger("S"), new Action(movePaddleScript, sKey));
	}
    private void initSprites() {
    }
		
}