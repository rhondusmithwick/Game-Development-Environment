package view;

import api.IEntity;
import api.ISystemManager;
import api.IView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
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
import model.component.visual.Sprite;
import model.core.SystemManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

	private final ConsoleTextArea console = new ConsoleTextArea();
	private final Button evaluateButton = new Button("Evaluate");
	private final Button loadButton = new Button("Load");
	// private final ScriptEngine engine = new
	// ScriptEngineManager().getEngineByName("Groovy");
	private Group root = new Group();
	private ISystemManager model = new SystemManager(root);
	private BorderPane pane;
	private SubScene subScene;
	private ViewUtilities viewUtils;

	@Deprecated
	public View(ISystemManager model, ScrollPane scene) {
		this(model, new Group(), 2000, 2000, scene);
	}

	@Deprecated
	public View(ISystemManager model, Group root, double width, double height, ScrollPane scene) {
		this.model = model;
		this.initConsole();
		this.initButtons();
		this.viewUtils = new ViewUtilities(root, model.getEntitySystem());
		this.subScene = this.createSubScene(root, width, height);
		
		this.pane = this.createBorderPane(root, this.subScene);
		viewUtils.allowDragging();
		viewUtils.allowDeletion();
		
		this.startTimeline();
	}

	@Deprecated
	public View() {
		this(2000, 2000);
	}

	@Deprecated
	public View(double width, double height) { // TODO: Scene
		this.initConsole();
		this.initButtons();
		this.viewUtils = new ViewUtilities(root, model.getEntitySystem());
		this.subScene = this.createSubScene(root, width, height);

		this.pane = this.createBorderPane(root, this.subScene);
		viewUtils.allowDragging();
		viewUtils.allowDeletion();

		this.startTimeline();
	}

	public void setScene(Scene scene) {
		scene.setOnKeyPressed(e -> model.getEntitySystem().getEventSystem().takeInput(e)); // TODO: add all inputs
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

	private SubScene createSubScene(Group root, double width, double height) {
		this.root = root;
		SubScene subScene = new SubScene(root, width, height);
		// TODO: not printing key presses, why?!
		// subScene.setOnMouseClicked(e -> System.out.println(e.getX()));
		// scene.setOnKeyTyped(e -> System.out.println(e.getCode()));
		// scene.setOnKeyReleased(e -> System.out.println(e.getCode()));
		// scene.setOnKeyPressed(e -> System.out.println(e.getCode()));
		
		return subScene;
	}

	private void modulateZLevel(IEntity e, ObservableList<Node> imageViews) {
		Sprite display = e.getComponent(Sprite.class);
		ImageView imageView = display.getImageView();
		imageViews.remove(imageView); // important

		int z = display.getZLevel();
		int index = imageViews.indexOf(imageView);
		switch(z) {
			case -2:
				imageViews.add(0, imageView);
				break;
			case -1:
				if(index-1>=0) {
					root.getChildren().add(index - 1, imageView);
				}
				break;
			case 1:
				if(index+1<imageViews.size()) {
					root.getChildren().add(index + 1, imageView);
					break;
				}
			case 2:
				imageViews.add(imageView); // to end of list
				break;
		}
	}

	private ImageView getUpdatedImageView(IEntity e) {
		Position pos = e.getComponent(Position.class);
		Sprite display = e.getComponent(Sprite.class);
		ImageView imageView = display.getImageView();

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
//		root.getChildren().clear();
		ObservableList<Node> imageViews = root.getChildren();
		List<Node> tempList = new ArrayList<>();
		Set<IEntity> renderableEntities = model.getEntitySystem().getEntitiesWithComponents(Sprite.class, Position.class);
		for (IEntity e : renderableEntities) {
			viewUtils.makeSelectable(e);
			tempList.addAll(this.getCollisionShapes(e));
			ImageView imageView = this.getUpdatedImageView(e);
			tempList.add(imageView);
			modulateZLevel(e, imageViews);
			if(!imageViews.contains(imageView)) { // populate root with new sprites
				imageViews.add(imageView);
			}
		}
		for(int i=0; i<imageViews.size(); i++) { // remove old sprites
			Node imageView = imageViews.get(i);
			if(!tempList.contains(imageView)) {
				imageViews.remove(imageView);
				i--;
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
