package view;

import api.IEntity;
import api.ISystemManager;
import api.IView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.component.movement.Orientation;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.component.visual.Sprite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author Tom
 *
 */
public class View implements IView {

	// TODO: resource file
	private final double MILLISECOND_DELAY = 10;
	private final double SECOND_DELAY = MILLISECOND_DELAY / 1000;
	private final double gapSize = 1;

	private Group root = new Group();
	private final ConsoleTextArea console = new ConsoleTextArea();
	private final Button evaluateButton = new Button("Evaluate");
	private final Button loadButton = new Button("Load");
	// private final ScriptEngine engine = new
	// ScriptEngineManager().getEngineByName("Groovy");
	private final ISystemManager model;
	private BorderPane pane;
	private SubScene subScene;
	private ViewUtilities viewUtils;

	public View(ISystemManager model, ScrollPane scene) {
		this(model, new Group(), 2000, 2000, scene);
	}

	public View(ISystemManager model, Group root, double width, double height, ScrollPane scene) {
		this.model = model;
		this.initConsole();
		this.initButtons();
		this.viewUtils = new ViewUtilities(root, model.getEntitySystem());
		this.subScene = this.createSubScene(root, width, height, scene);	
		
		this.pane = this.createBorderPane(root, this.subScene);
		viewUtils.allowDragging();
		viewUtils.allowDeletion();
		
		this.startTimeline();
	}

	public Pane getPane() {
		return this.pane;
	}

	public SubScene getSubScene() {
		return this.subScene;
	}

	private void startTimeline() {
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY));
		Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(frame);
		timeline.play();
	}

	private SubScene createSubScene(Group root, double width, double height, ScrollPane scene) {
		this.root = root;
		SubScene subScene = new SubScene(root, width, height);
		// TODO: not printing key presses, why?! Remove last arg after bugfix.
		// subScene.setOnMouseClicked(e -> System.out.println(e.getX()));
		// scene.setOnKeyTyped(e -> System.out.println(e.getCode()));
		// scene.setOnKeyReleased(e -> System.out.println(e.getCode()));
		// scene.setOnKeyPressed(e -> System.out.println(e.getCode()));
		
		return subScene;
	}

	private ImageView getUpdatedImageView(IEntity e) {
		Sprite display = e.getComponent(Sprite.class);
		ImageView imageView = display.getImageView();
		int z = display.getZLevel();
		if (z == -1) {
			imageView.toBack();
		} else if (z == 1) {
			imageView.toFront();
		}

		Position pos = e.getComponent(Position.class);
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
		Collection<Bounds> bounds = new ArrayList<>();
		for (Collision c : collisions) {
			bounds.add(c.getMask());
		}
		Collection<Shape> shapes = new ArrayList<>();
		for (Bounds b : bounds) {
			if (b == null) {
				System.out.println("null collide mask: " + e.getName());
				continue;
			}
			Shape r = new Rectangle(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
//			 double val = 1.0;// Math.random();
//			 r.setFill(new Color(val, val, val, val));
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.RED);
			r.setStrokeWidth(2);
			shapes.add(r);
		}
		return shapes;
	}

	private void step(double dt) { // game loop
		// simulate
		model.step(dt);

		// render
		root.getChildren().clear();
		for (IEntity e : model.getEntitySystem().getAllEntities()) {
			if (e.hasComponents(Sprite.class, Position.class)) {
				viewUtils.makeSelectable(e);
				root.getChildren().addAll(this.getCollisionShapes(e));
				root.getChildren().add(this.getUpdatedImageView(e));
			}
		}
	}

	private BorderPane createBorderPane(Group root, SubScene subScene) {
		BorderPane pane = new BorderPane();
		ScrollPane center = new ScrollPane();
		pane.setPadding(new Insets(gapSize, gapSize, gapSize, gapSize));
		pane.setCenter(center);
		// center.setContent(root);
		center.setContent(subScene);
		System.out.println(subScene.getRoot());
		root.setManaged(false); // IMPORTANT

		// center.setPannable(true);
		center.setVbarPolicy(ScrollBarPolicy.NEVER);
		center.setHbarPolicy(ScrollBarPolicy.NEVER);

		BorderPane inputPane = new BorderPane();
		inputPane.setTop(console);
		inputPane.setBottom(evaluateButton);
		// inputPane.setRight(loadButton);
		pane.setBottom(inputPane);
		return pane;
	}

	private void initConsole() {
		console.appendText("\n");
		
		console.setOnKeyPressed(e -> {
			KeyCode keyCode = e.getCode();
			if (keyCode == KeyCode.ENTER) {
				this.evaluate();
				e.consume();
			}
		});
		
	}

	private void initButtons() {
		// evaluateButton.setText("Evaluate");
		evaluateButton.setOnAction(e -> this.evaluate());
		loadButton.setOnAction(e -> this.load());
	}

	private void load() { // TODO: loading
		// XMLReader<ISystemManager>().readSingleFromFile("demo.xml");
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

}
