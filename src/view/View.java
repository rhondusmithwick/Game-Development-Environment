// This entire file is part of my masterpiece.
// TOM WU

// I co-authored and refactored this class with Bruna and Ben, and now, I improved it by delegating
// many methods to other classes (HUDRenderer and ViewCreator). The length of this class was over 300 lines
// before this latest refactor. I have learned about the importance of single responsibility principle,
// and this class adheres more closely to this principle now. The main reason I have refactored View is
// that it serves as the primary interface between the frontend and backend.

package view;

import api.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import model.component.movement.Orientation;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.component.visual.AnimatedSprite;
import model.component.visual.Sprite;
import model.core.SystemManager;
import update.GameLoopManager;
import view.utilities.HUDRenderer;
import view.utilities.SpriteUtilities;
import view.utilities.ToMainMenu;
import view.utilities.ViewCreator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Tom
 * @author Bruna
 * @author Ben
 */

public class View implements IView {

//    private ConsoleTextArea console;
    private BorderPane pane;
    private ConsoleTextArea console;
    private HBox buttonBox;
    private GameLoopManager manager;
    private ResourceBundle myResources;
    private final ResourceBundle viewProperties = ResourceBundle.getBundle("propertyFiles/view");
    private final boolean editingOn;
    private final PopUpUtilities popUps = new PopUpUtilities();
    private final ViewUtilities viewUtils = new ViewUtilities();
    private ISystemManager model;
    private Group root = new Group();
    private Scene scene;
    private DragAndResizeDynamic dragAndResizeDynamic;

    public View (double viewWidth, double viewHeight, double sceneWidth, double sceneHeight, ILevel level,
                 String language, boolean editingOn) {
        // init GUI
        init(viewWidth, viewHeight, sceneWidth, sceneHeight, level, language);
        scene = new Scene(pane, sceneWidth, sceneHeight);
        model = new SystemManager(scene, level);
        manager = new GameLoopManager(language, model);

        this.editingOn = editingOn;
        if (this.editingOn) {
            dragAndResizeDynamic = new DragAndResizeDynamic();
            dragAndResizeDynamic.makeRootDragAndResize(root);
        }

        // start game loop
        double secondDelay = 1.0/getFramerate();
        ViewFeatureMethods.startTimeline(secondDelay*1000, e -> step(secondDelay));
    }

    private void init(double viewWidth, double viewHeight, double sceneWidth, double sceneHeight, ILevel level, String language) {
        SubScene subScene = createSubScene(root, viewWidth, viewHeight);
        root.setManaged(false); // IMPORTANT

        myResources = ResourceBundle.getBundle(language);
        console = ViewCreator.initConsole(myResources, e -> {
            KeyCode keyCode = e.getCode();
            if (keyCode == KeyCode.ENTER) {
                evaluate();
                e.consume();
            }
        });
        buttonBox = ViewCreator.initButtons(myResources, new HashMap<String, EventHandler<ActionEvent>>() {{
//            put("evaluate", e -> evaluate());
            put("loopManager", e -> manager.show());
            put("mainMenu", e -> ToMainMenu.toMainMenu(pane));
            put("startGameLoop", e -> model.play());
            put("pauseGameLoop", e -> model.pauseLoop());
        }});
        pane = ViewCreator.createMainBorderPane(subScene, viewProperties, editingOn, console, buttonBox);
    }

    private double getFramerate() {
        double framerate;
        try {
            framerate = Double.parseDouble(viewProperties.getString("FramesPerSecond"));
        } catch (NumberFormatException e) {
            framerate = 60; // DEFAULT
        }
        return framerate;
    }

    public Pane getPane () {
        return this.pane;
    }

    private SubScene createSubScene (Group root, double width, double height) {
        this.root = root;
        SubScene subScene = new SubScene(root, width, height);
        subScene.setFill(Color.WHITE);
        subScene.setOnMouseClicked(popUps::deletePopUps);
        return subScene;
    }

    @Override
    public IEntitySystem getEntitySystem () {
        return model.getLevel().getEntitySystem();
    }

    @Override
    public ILevel getLevel () {
        return model.getLevel();
    }

    @Override
    public void setScene (Scene scene) {
        this.scene = scene;
        model.getLevel().setOnInput(scene);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }

    private ImageView getUpdatedImageView (IEntity e) {
        Position pos = e.getComponent(Position.class);
        ImageView imageView = SpriteUtilities.getImageView(e);
        imageView.setId(e.getID());
        imageView.setTranslateX(pos.getX());
        imageView.setTranslateY(pos.getY());
        if (e.hasComponent(Orientation.class)) {
            Orientation o = e.getComponent(Orientation.class);
            if(o.getOrientationString().equals("west")) {
                imageView.setScaleX(-1);
            }
        }
        return imageView;
    }

    private Collection<Shape> getCollisionShapes (IEntity e) {
        List<Collision> collisions = e.getComponentList(Collision.class);
        Collection<Shape> shapes = new ArrayList<>();
        if (!collisions.isEmpty()) {
            Collection<Bounds> bounds = collisions.stream().map(c -> c.getMask()).collect(Collectors.toCollection(ArrayList::new));
            bounds.stream().filter(b -> b != null).forEach(b -> {
                Shape r = new Rectangle(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
                r.setFill(Color.TRANSPARENT);
                r.setStroke(Color.RED);
                r.setStrokeWidth(2);
                shapes.add(r);
            });
        }
        return shapes;
    }

    @SuppressWarnings("unchecked")
    private void step (double dt) {
        // simulate
        model.step(dt);
        // render
        root.getChildren().clear();
        List<IEntity> entities = model.getEntitySystem().getAllEntities();
        for (IEntity e : entities) {
            if ((e.hasComponent(Sprite.class)||e.hasComponent(AnimatedSprite.class)) && e.hasComponent(Position.class)) {
                if (editingOn) {
                    root.getChildren().addAll(getCollisionShapes(e));
                    dragAndResizeDynamic.makeEntityDragAndResize(e);
                }
                ImageView imageView = getUpdatedImageView(e);
                imageView.setOnContextMenuRequested(event -> popUps.showPopUp(e, event, myResources, getEntitySystem()));
                if (!root.getChildren().contains(imageView)) {
                    root.getChildren().add(imageView);
                }
            }
        }
        HUDRenderer.renderHUD(entities, myResources, root);
    }

    private void evaluate () {
        String text = console.getText();
        String command = text.substring(text.lastIndexOf("\n")).trim();
        console.println("\n"+viewProperties.getString("ConsoleCommandSeparator"));
        try {
            Object result = model.getShell().evaluate(command);
            if (result != null) {
                console.println(result.toString());
            } else {
                console.println(viewProperties.getString("ConsoleOutputNull"));
            }
        } catch (Exception e) {
            console.println(e.getMessage());
        }
        console.println();
    }

    @Override
    public void toggleHighlight (IEntity entity) {
        viewUtils.toggleHighlight(entity);
    }

    @Override
    public void highlight (IEntity entity) {
        viewUtils.highlight(entity);
    }

    @Override
    public void dehighlight (IEntity entity) {
        viewUtils.dehighlight(entity);
    }

}
