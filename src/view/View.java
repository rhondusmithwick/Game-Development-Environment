package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import api.IEntity;
import api.ISystemManager;
import api.IView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.component.movement.Orientation;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.component.visual.ImagePath;

/**
 * 
 * @author Tom
 *
 */
public class View implements IView {

	private final double MILLISECOND_DELAY = 10;
	private final double SECOND_DELAY = MILLISECOND_DELAY / 1000;
	private final double gapSize = 10;

	private final Group root = new Group();
	private final ConsoleTextArea console = new ConsoleTextArea();
	private final Button evaluateButton = new Button("Evaluate");
	private final Button loadButton = new Button("Load");
	// private final ScriptEngine engine = new
	// ScriptEngineManager().getEngineByName("Groovy");
	private final ISystemManager model;
	private final BorderPane pane;

	public View(ISystemManager model) {
		this.model = model;
		this.initEngine();
		this.initConsole();
		this.initButtons();
		this.pane = this.createBorderPane();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY));
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public Pane getPane() {
		return this.pane;
	}

	private ImageView getUpdatedImageView(IEntity e) {
		ImagePath display = e.getComponent(ImagePath.class);
		ImageView imageView = display.getImageView();

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
		Collection<Bounds> bounds = new ArrayList<Bounds>();
		for (Collision c : collisions) {
			bounds.add(c.getMask());
		}
		Collection<Shape> shapes = new ArrayList<Shape>();
		for (Bounds b : bounds) {
			if (b == null) {
				System.out.println(e.getName());
				continue;
			}
			Shape r = new Rectangle(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
			double val = Math.random();
			r.setFill(new Color(val, val, val, val));
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
			if (e.hasComponents(ImagePath.class, Position.class)) {
				root.getChildren().add(this.getUpdatedImageView(e));
				root.getChildren().addAll(this.getCollisionShapes(e));
			}
		}
	}

	private BorderPane createBorderPane() {
		BorderPane pane = new BorderPane();
		ScrollPane center = new ScrollPane();
		pane.setPadding(new Insets(gapSize, gapSize, gapSize, gapSize));
		pane.setCenter(center);
		center.setContent(root);
		// root.setLayoutX(0);
		// root.setLayoutY(0);
		root.setManaged(false); // IMPORTANT

		center.setPannable(true);
		// center.setFitToHeight(false);
		// center.setFitToWidth(false);
		center.setVbarPolicy(ScrollBarPolicy.NEVER);
		center.setHbarPolicy(ScrollBarPolicy.NEVER);

		// GridPane inputPane = new GridPane();
		// inputPane.add(console, 0, 0);
		// inputPane.add(evaluateButton, 0, 1);
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

	private void initEngine() { // TODO: make it possible to import classes
								// directly within shell
	}

	private void load() { // TODO: load from "demo.xml"
		// this.model = new
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
