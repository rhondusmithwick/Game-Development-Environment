package testing.AniPong;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import events.Action;
import events.KeyTrigger;
import events.MouseTrigger;
import model.component.character.Score;
import model.component.character.UserControl;
import model.component.movement.Position;
import model.component.visual.Sprite;
import model.core.SystemManager;
import api.IEntity;
import api.IEventSystem;
import api.ILevel;
import api.IPhysicsEngine;
import api.ISystemManager;

/**
 * 
 * @author Tom
 *
 */
public class AniPong {
	public static final String TITLE = "Ani's (and Tom's) Pong game";
    public static final String PATH = "src/testing/AniPong/";
    private final String movePaddleUpScript = PATH + "MovePaddleUp.groovy";
    private final String movePaddleDownScript = PATH + "MovePaddleDown.groovy";
    private final String stopPaddleScript = PATH + "StopPaddle.groovy";
    private final int winningScore = 3;
    private Scene myScene;
    private Group root;
	private ISystemManager game = new SystemManager();
	private ILevel universe;
	private IPhysicsEngine physics;
    private IEventSystem events;

	public Scene init(int width, int height) {
		
		// Create a scene graph to organize the scene
		this.game = game;
		this.universe = game.getEntitySystem();
        this.events = universe.getEventSystem();
        this.physics = universe.getPhysicsEngine();
        initKeyInputs();
        initSprites();
        root = new Group();
        // Create a place to see the shapes
        myScene = new Scene(root, width, height, Color.WHITE);
        universe.setOnInput(myScene);
        return myScene;
		
	}

    private void initSprites() {
        // Ball
        IEntity ball = AniSpriteLoader.createBall("Ball", new Position(200.0, 150.0));
        //Paddles
        IEntity leftPaddle = AniSpriteLoader.createPaddle("LeftPaddle", new Position(100, 160));
        IEntity rightPaddle = AniSpriteLoader.createPaddle("RightPaddle", new Position(540, 160));
//        events.registerEvent(new MouseTrigger(leftPaddle.getID()), new Action(movePaddleUpScript));
        rightPaddle.addComponent(new UserControl());
        // Walls
        IEntity leftWall = AniSpriteLoader.createPlatform("LeftWall", new Position(-578, 7));
        IEntity rightWall = AniSpriteLoader.createPlatform("RightWall", new Position(686, 7));
        IEntity ceiling = AniSpriteLoader.createPlatform("Ceiling", new Position(7, -500));
        IEntity floor = AniSpriteLoader.createPlatform("Floor", new Position(7, 500));

        universe.addEntities(ball, leftPaddle, rightPaddle, leftWall, rightWall, ceiling, floor);
    }

    private void initKeyInputs() {
        Map<String, Object> wKey = new HashMap<>();
        wKey.put("key", "W");
        Map<String, Object> sKey = new HashMap<>();
        wKey.put("key", "S");
        Map<String, Object> mKey = new HashMap<>();
        wKey.put("key", "M");
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("W"), KeyEvent.KEY_PRESSED), new Action(movePaddleUpScript, wKey));
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("S"), KeyEvent.KEY_PRESSED), new Action(movePaddleDownScript, sKey));
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("W"), KeyEvent.KEY_RELEASED), new Action(stopPaddleScript, mKey));
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("S"), KeyEvent.KEY_RELEASED), new Action(stopPaddleScript, mKey));
        
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("W"), KeyEvent.KEY_PRESSED), new Action(movePaddleUpScript, wKey));
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("S"), KeyEvent.KEY_PRESSED), new Action(movePaddleDownScript, sKey));
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("W"), KeyEvent.KEY_RELEASED), new Action(stopPaddleScript, mKey));
        events.registerEvent(new KeyTrigger(KeyCode.getKeyCode("S"), KeyEvent.KEY_RELEASED), new Action(stopPaddleScript, mKey));
        events.registerEvent(new MouseTrigger(MouseButton.PRIMARY, MouseEvent.MOUSE_CLICKED), new Action(stopPaddleScript));
        //System.out.println("Input keys cannot be registered without de-serialization error.");
        String s = events.getEventsAsString();
    }

//    private initGlobalVariables() {
//        IEntity data = new Entity("data");
//        data.addComponent()
//    }

	public void step(double dt) {
		root.getChildren().clear();
		physics.update(universe, dt);
        events.updateInputs(dt);
        updateScores();
        universe.getAllEntities().stream().forEach(e->drawCharacter(e));
	}

    private void updateScores() {
        for(IEntity e:universe.getEntitiesWithComponents(Score.class)) {
            Score score = e.getComponent(Score.class);
            if(score.getScore()==winningScore) {
                System.out.println(e.getName()+" has won.");
            }
        }
    }
    
    public ImageView drawCharacter(IEntity character) {
        Sprite imgPath = character.getComponent(Sprite.class);
        ImageView charSprite = imgPath.getImageView();
        charSprite.setPreserveRatio(true);
        //imgPath.setImageHeight(100);
        charSprite.setPreserveRatio(true);
        root.getChildren().add(charSprite);
        return charSprite;
    }

	public String getTitle() {
		return TITLE;
	}

}
