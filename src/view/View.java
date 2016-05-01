package view;

import api.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.component.movement.Orientation;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.component.visual.Sprite;
import model.core.SystemManager;
import update.GameLoopManager;
import view.utilities.ButtonFactory;
import view.utilities.PopUp;
import view.utilities.SpriteUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

/**
 * 
 * @author Tom
 *
 */

public class View implements IView {

	private final double MILLISECOND_DELAY = 10;
	private final double SECOND_DELAY = MILLISECOND_DELAY / 1000;
	private final double gapSize = 1;

	private final ConsoleTextArea console = new ConsoleTextArea();
	// private final ScriptEngine engine = new
	// ScriptEngineManager().getEngineByName("Groovy");
	private Group root = new Group();
	private ISystemManager model;
	private BorderPane pane;
	private SubScene subScene;
	private ViewUtilities viewUtils;
	private DragAndResizeDynamic DandR;
	private GameLoopManager manager;
	private HBox buttonBox = new HBox();
	private ResourceBundle myResources;

	@Deprecated
	public View(String language) {
		// this(2000, 2000, new Level(), language);
	}

	public View(double width, double height, ILevel level, String language, Scene scene) {
		subScene = this.createSubScene(root, width, height);
		model = new SystemManager(subScene, level);
		myResources = ResourceBundle.getBundle(language);
		manager = new GameLoopManager(language, model);
		initConsole();
		initButtons();
		pane = createMainBorderPane(root, this.subScene);
		model.getLevel().setOnInput(scene);
		viewUtils = new ViewUtilities();
		DandR = new DragAndResizeDynamic();
		DandR.makeRootDragAndResize(root);
		ViewFeatureMethods.startTimeline(MILLISECOND_DELAY, e -> step(SECOND_DELAY));
		//this.startTimeline();
	}

	private void createLoopManager() {
		manager.show();
	}

	public void setScene(Node scene) {
		scene.setOnKeyPressed(e -> keyPressed(e.getCode()));
	}

	public Pane getPane() {
		return this.pane;
	}

	public SubScene getSubScene() {
		return this.subScene;
	}

	public void startTimeline() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY));
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}

	private SubScene createSubScene(Group root, double width, double height) {
		this.root = root;
		SubScene subScene = new SubScene(root, width, height);
		subScene.setFill(Color.WHITE);
		return subScene;
	}

	private void keyPressed(KeyCode code) {
		System.out.println("\t\t" + code);
	}

	public void toggleHighlight(IEntity entity) {
		viewUtils.toggleHighlight(entity);
	}

	@Override
	public IEntitySystem getEntitySystem() {
		return model.getLevel().getEntitySystem();
	}

	@Override
	public ILevel getLevel() {
		return model.getLevel();
	}

	public void highlight(IEntity entity) {
		viewUtils.highlight(entity);
	}

	private ImageView getUpdatedImageView(IEntity e) {
		Position pos = e.getComponent(Position.class);
//		Sprite display = e.getComponent(Sprite.class);
		ImageView imageView = SpriteUtilities.getImageView(e); //display.getImageView();
		imageView.setId(e.getID());
		imageView.setTranslateX(pos.getX());
		imageView.setTranslateY(pos.getY());
		if (e.hasComponent(Orientation.class)) {
			Orientation o = e.getComponent(Orientation.class);
			imageView.setRotate(o.getOrientation());
		}
		return imageView;
	}

	private Collection<Shape> getCollisionShapes(IEntity e) {
		List<Collision> collisions = e.getComponentList(Collision.class);
		Collection<Shape> shapes = new ArrayList<>();
		if(collisions.isEmpty()) {
			return shapes;
		}
		Collection<Bounds> bounds = new ArrayList<>();
		for (Collision c : collisions) {
			bounds.add(c.getMask());
		}
		for (Bounds b : bounds) {
			if (b == null) {
			//	System.out.println("null collide mask: " + e.getName());
				continue;
			}
			Shape r = new Rectangle(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
			// double val = 1.0;// Math.random();
			// r.setFill(new Color(val, val, val, val));
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.RED);
			r.setStrokeWidth(2);
			shapes.add(r);
		}
		return shapes;
	}

	@SuppressWarnings("unchecked")
	private void step(double dt) { // game loop
		model.step(dt);

		root.getChildren().clear();
		List<IEntity> entities = model.getEntitySystem().getAllEntities();// .getEntitiesWithComponents(Sprite.class,
																			// Position.class);
		for (IEntity e : entities) {
			if (e.hasComponents(Sprite.class, Position.class)) {
				root.getChildren().addAll(getCollisionShapes(e));
				DandR.makeEntityDragAndResize(e);
				ImageView imageView = getUpdatedImageView(e);
				imageView.setOnContextMenuRequested(event -> showPopUp(e,event));
				root.getChildren().add(imageView);
				if (!root.getChildren().contains(imageView)) {
					root.getChildren().add(imageView);
				}
			}
		}
	}

	private BorderPane createMainBorderPane(Group root, SubScene subScene) {
		BorderPane pane = new BorderPane();
		ScrollPane center = new ScrollPane();
		root.setManaged(false); // IMPORTANT
		pane.setPadding(new Insets(gapSize, gapSize, gapSize, gapSize));
		pane.setCenter(center);
		center.setContent(subScene);
		center.setVbarPolicy(ScrollBarPolicy.NEVER);
		center.setHbarPolicy(ScrollBarPolicy.NEVER);
		pane.setBottom(setUpInputPane());
		return pane;
	}

	private BorderPane setUpInputPane() {
		BorderPane pane = new BorderPane();
		pane.setTop(console);
		pane.setBottom(buttonBox);
		return pane;
	}

	private void initButtons() {
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("evaluate"), e -> this.evaluate()));
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("load"), e -> this.load()));
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("loopManager"), e -> this.createLoopManager()));
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("startGameLoop"), e -> this.model.play()));
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("pauseGameLoop"), e -> this.model.pauseLoop()));
	}

	private void load() { // TODO: loading
		// XMLReader<ISystemManager>().readSingleFromFile("demo.xml");
	}

	private void initConsole() {
		console.setText(myResources.getString("enterCommands"));
		console.appendText("\n\n");

		console.setOnKeyPressed(e -> {
			KeyCode keyCode = e.getCode();
			if (keyCode == KeyCode.ENTER) {
				this.evaluate();
				e.consume();
			}
		});
	}
	
	public void showPopUp(IEntity entity, ContextMenuEvent event) {
		
		Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<String, EventHandler<ActionEvent>>();
		menuMap.put(myResources.getString("remove"), e -> ViewFeatureMethods.removeFromDisplay(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendBack"), e -> ViewFeatureMethods.sendToBack(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendFront"), e -> ViewFeatureMethods.sendToFront(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendBackOne"), e ->ViewFeatureMethods.sendBackward(entity, getEntitySystem()));
		menuMap.put(myResources.getString("sendForwardOne"), e -> ViewFeatureMethods.sendForward(entity, getEntitySystem()));
		
		PopUp myPopUp = new PopUp(120, 180);
		myPopUp.show(setPopUp(menuMap),event.getScreenX(),event.getScreenY());
	}
	
	public ScrollPane setPopUp(Map<String,EventHandler<ActionEvent>> map){
		VBox box = new VBox();
		for (Entry<String, EventHandler<ActionEvent>> entry : map.entrySet()){
			Button button = ButtonFactory.makeButton(entry.getKey(), entry.getValue());
			button.setMaxWidth(Double.MAX_VALUE);
			box.getChildren().add(button);
		}
		return new ScrollPane(box);
	}

	private void evaluate() {
		String text = console.getText();
		String command = text.substring(text.lastIndexOf("\n")).trim();
		console.println("\n----------------");
		try {
			// Object result = engine.eval(command);
			Object result = model.getShell().evaluate(command);
			if (result != null) {
				console.println(result.toString());
			}
		} catch (Exception e) {
			console.println(e.getMessage());
		}
		console.println();
	}

	@Override
	public void dehighlight(IEntity entity) {
		viewUtils.dehighlight(entity);
	}
	
}
