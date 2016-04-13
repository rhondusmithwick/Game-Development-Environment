package testing.demo;

import java.util.Collection;

import api.IEntity;
import api.IEntitySystem;
import api.ISystemManager;
import groovy.lang.GroovyShell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.component.movement.Position;
import model.component.visual.ImagePath;
import usecases.SystemManager;

public class View {

	private final double MILLISECOND_DELAY = 10;
	private final double SECOND_DELAY = MILLISECOND_DELAY / 1000;
	private final double gapSize = 10;

	private final Stage myStage;
	private final Group root = new Group();
	private final ConsoleTextArea console = new ConsoleTextArea();
	private final Button evaluateButton = new Button("Evaluate");
	private final Button loadButton = new Button("Load");
	private final GroovyShell shell = new GroovyShell();
	private ISystemManager model = new SystemManager();

	public View(Stage stage) {
		this.myStage = stage;
		this.initEngine();
		this.initConsole();
		this.initButtons();
		BorderPane pane = this.createBorderPane();
		Scene scene = new Scene(pane, 500, 500);
		stage.setScene(scene);
		stage.show();

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step());
		Timeline animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private void step() { // game loop
		// simulate
		model.step(SECOND_DELAY);

		// render
		root.getChildren().clear();
		IEntitySystem universe = model.getEntitySystem();
		Collection<IEntity> entities = universe.getEntitiesWithComponents(Position.class, ImagePath.class);
		entities.stream().forEach(e -> {
			Position pos = e.getComponent(Position.class);
			ImagePath display = e.getComponent(ImagePath.class);
			ImageView imageView = display.getImageView();
			imageView.setTranslateX(pos.getX());
			imageView.setTranslateY(pos.getY());
			root.getChildren().add(imageView);
		});
	}

	private BorderPane createBorderPane() {
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(gapSize, gapSize, gapSize, gapSize));
		pane.getChildren().add(root);

		// GridPane inputPane = new GridPane();
		// inputPane.add(console, 0, 0);
		// inputPane.add(evaluateButton, 0, 1);
		BorderPane inputPane = new BorderPane();
		inputPane.setTop(console);
		inputPane.setBottom(evaluateButton);
		inputPane.setRight(loadButton);
		pane.setBottom(inputPane);
		return pane;
	}

	private void initConsole() {
		console.appendText("\n");
		console.setOnKeyPressed(e -> {
			KeyCode keyCode = e.getCode();
			if (keyCode == KeyCode.ENTER) {
				this.evaluate();
			}
			e.consume();
		});
	}

	private void initButtons() {
		// evaluateButton.setText("Evaluate");
		evaluateButton.setOnAction(e -> this.evaluate());
		loadButton.setOnAction(e -> this.load());
	}

	private void initEngine() { // TODO: make it possible to import classes
								// directly within shell
		// Binding binding = new Binding();
		// binding.setVariable("game", this.model);
		// binding.setVariable("demo", new GroovyDemoTest());
		shell.setVariable("game", this.model);
		shell.setVariable("universe", this.model.getEntitySystem());
		shell.setVariable("demo", new GroovyDemoTest());
		// this.engine.put("game", this.model);
		// this.engine.put("universe", this.model.getEntitySystem());
		// this.engine.put("c0", (new GroovyDemoTest()).getCharacter0());
		// this.engine.put("c1", (new GroovyDemoTest()).getCharacter1());
	}

	private void load() { // TODO: load from "demo.xml"
		// this.model = ?
	}

	private void evaluate() {
		String text = console.getText();
		String command = text.substring(text.lastIndexOf("\n")).trim();
		console.println("\n----------------");
		try {
			Object result = shell.evaluate(command);
			if (result != null) {
				console.print(result.toString());
			}
		} catch (Exception e) {
			console.println(e.getMessage());
		}
		console.println();
	}

}
