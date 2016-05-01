package view;

import api.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Vooga;
import model.component.movement.Orientation;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.component.visual.Sprite;
import model.core.SystemManager;
import model.entity.Level;
import update.GameLoopManager;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.SpriteUtilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

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
	private Group root = new Group();
	private ISystemManager model;
	private BorderPane pane;
	private SubScene subScene;
	private ViewUtilities viewUtils;
	private DragAndResizeDynamic DandR;
	private GameLoopManager manager;
	private HBox buttonBox = new HBox();
	private ResourceBundle myResources;
	private boolean debug;
	private Scene scene;

	public View(double viewWidth, double viewHeight, double sceneWidth, double sceneHeight, ILevel level, String language, boolean debug) {
		subScene = this.createSubScene(root, viewWidth, viewHeight);
		this.debug=debug;
		myResources = ResourceBundle.getBundle(language);
		initConsole();
		initButtons();
		pane = createMainBorderPane(root, this.subScene);
		scene = new Scene(pane, sceneWidth, sceneHeight);
		model = new SystemManager(scene, level);
		manager = new GameLoopManager(language, model);
		viewUtils = new ViewUtilities();
		if(debug){
			DandR = new DragAndResizeDynamic();
			DandR.makeRootDragAndResize(root);
		}
		this.startTimeline();
	}

	private void createLoopManager() {
		manager.show();
	}
	@Override
	public void setScene(Scene scene) {
		model.getLevel().setOnInput(scene);
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
		subScene.setFill(Color.WHITE);
		return subScene;
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
	
	public Scene getScene() {
		return scene;
	}

	private ImageView getUpdatedImageView(IEntity e) {
		Position pos = e.getComponent(Position.class);
		ImageView imageView = SpriteUtilities.getImageView(e);
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
				continue;
			}
			Shape r = new Rectangle(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.RED);
			r.setStrokeWidth(2);
			shapes.add(r);
		}
		return shapes;
	}

	@SuppressWarnings("unchecked")
	private void step(double dt) { 
		model.step(dt);
		root.getChildren().clear();
		List<IEntity> entities = model.getEntitySystem().getAllEntities();
		for (IEntity e : entities) {
			if (e.hasComponents(Sprite.class, Position.class)) {
				if(debug){
					root.getChildren().addAll(getCollisionShapes(e));
				}
				if(debug){
					DandR.makeEntityDragAndResize(e);
				}
				ImageView imageView = getUpdatedImageView(e);
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
		root.setManaged(false);
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
		if(debug){
			pane.setTop(console);
		}
		pane.setBottom(buttonBox);
		return pane;
	}

	private void initButtons() {
		if(debug){
			buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("evaluate"), e -> this.evaluate()));
			buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("loopManager"), e -> this.createLoopManager()));
		}
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("mainMenu"), e -> this.mainMenu()));
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("startGameLoop"), e -> this.model.play()));
		buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("pauseGameLoop"), e -> this.model.pauseLoop()));
	}

	private void mainMenu() { 
        Stage myStage = (Stage) pane.getScene().getWindow();
        myStage.setWidth(GUISize.MAIN_SIZE.getSize());
        myStage.setHeight(GUISize.MAIN_SIZE.getSize());
        Vooga vooga = new Vooga(myStage);
        vooga.init();
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

	private void evaluate() {
		String text = console.getText();
		String command = text.substring(text.lastIndexOf("\n")).trim();
		console.println("\n----------------");
		try {
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
