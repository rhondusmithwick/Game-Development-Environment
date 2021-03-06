package testing.demo

import api.*
import events.Action
import events.KeyTrigger
import model.component.movement.Position

public class MoveAndSound implements IGameScript {

    private ISystemManager game;
    private ILevel universe;
//    private final IPhysicsEngine physics = new PhysicsEngine();
    public static final String PATH = "src/testing/demo/";

    private final String punchScript = PATH + "Punch.groovy";

    private IEventSystem events;

    @Override
    public void init(GroovyShell shell, ISystemManager game) {
        this.game = game;
        this.universe = game.getLevel();
        this.events = universe.getEventSystem();
//		shell.setVariable("punchandsound");
        initKeyInputs();
        initSprites();
    }

    @Override
    public void update(double dt) {
//        physics.update(universe, dt);
    }

    private void initKeyInputs() {
        Map<String, Object> sKey = new HashMap<>();
        sKey.put("key", "S");
        Map<String, Object> wKey = new HashMap<>();
        wKey.put("key", "W");
        events.registerEvent(new KeyTrigger("W"), new Action(punchScript, wKey));
        events.registerEvent(new KeyTrigger("S"), new Action(punchScript, sKey));
    }

    private void initSprites() {
        IEntity sprite = SpriteLoader.createAnimatedSprite("ryu", new Position(50.0, 150.0));
        universe.addEntities(sprite);
    }

}
