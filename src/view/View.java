package view;

import api.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import model.component.hud.HUD;
import model.component.movement.Orientation;
import model.component.movement.Position;
import model.component.physics.Collision;
import model.core.SystemManager;
import update.GameLoopManager;
import view.editor.environmenteditor.DragAndResize;
import view.editor.environmenteditor.EnvironmentHelperMethods;
import view.editor.environmenteditor.HUDupdateUtility;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.PopUp;
import view.utilities.SpriteUtilities;
import view.utilities.ToMainMenu;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * @author Tom
 * @author Bruna
 * @author Ben
 */

public class View implements IView {

    private final double MILLISECOND_DELAY = 10;
    private final double SECOND_DELAY = MILLISECOND_DELAY / 1000;
    private final ConsoleTextArea console = new ConsoleTextArea();
    private final ISystemManager model;
    private final BorderPane pane;
    private final SubScene subScene;
    private final GameLoopManager manager;
    private final HBox buttonBox = new HBox();
    private final ResourceBundle myResources;
    private final boolean debug;
    private final Scene scene;
    private final List<PopUp> myPopUpList = new ArrayList<>();
    private Group root = new Group();
    private DragAndResize DandR;

    public View (double viewWidth, double viewHeight, double sceneWidth, double sceneHeight, ILevel level,
                 String language, boolean debug) {
        subScene = this.createSubScene(root, viewWidth, viewHeight);
        subScene.setOnMouseClicked(this::deletePopUps);
        this.debug = debug;
        myResources = ResourceBundle.getBundle(language);
        initConsole();
        initButtons();
        pane = createMainBorderPane(root, this.subScene);
        scene = new Scene(pane, sceneWidth, sceneHeight);
        model = new SystemManager(scene, level);
        manager = new GameLoopManager(language, model);
        if (this.debug) {
            DandR = new DragAndResize();
            DandR.makeRootDragAndResize(root);
        }
        EnvironmentHelperMethods.startTimeline(MILLISECOND_DELAY, e -> step(SECOND_DELAY));
    }

    private void deletePopUps (MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            myPopUpList.stream().forEach(PopUp::closeScene);
            myPopUpList.clear();
        }
    }

    private void createLoopManager () {
        manager.show();
    }

    public Pane getPane () {
        return this.pane;
    }

    public SubScene getSubScene () {
        return this.subScene;
    }

    @Deprecated
    public void startTimeline () {
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> this.step(SECOND_DELAY));
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private SubScene createSubScene (Group root, double width, double height) {
        this.root = root;
        SubScene subScene = new SubScene(root, width, height);
        subScene.setFill(Color.WHITE);
        return subScene;
    }

    public void toggleHighlight (IEntity entity) {
        EnvironmentHelperMethods.toggleHighlight(entity);
    }

    @Override
    public IEntitySystem getEntitySystem () {
        return model.getLevel().getEntitySystem();
    }

    @Override
    public ILevel getLevel () {
        return model.getLevel();
    }

    public void highlight (IEntity entity) {
        EnvironmentHelperMethods.highlight(entity);
    }

    public Scene getScene () {
        return scene;
    }

    @Override
    public void setScene (Scene scene) {
        model.getLevel().setOnInput(scene);
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
        if (collisions.isEmpty()) {
            return shapes;
        }
        Collection<Bounds> bounds = collisions.stream().map(c -> c.getMask()).collect(Collectors.toCollection(ArrayList::new));
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
    private void step (double dt) {
        // simulate
        model.step(dt);
        // render
        root.getChildren().clear();
        List<IEntity> entities = model.getEntitySystem().getAllEntities();
        for (IEntity e : entities) {
            if (SpriteUtilities.getSpriteComponent(e) != null && e.hasComponent(Position.class)) {
                if (debug) {
                    root.getChildren().addAll(getCollisionShapes(e));
                }
                if (debug) {
                    DandR.makeEntityDragAndResize(e);
                }
                ImageView imageView = getUpdatedImageView(e);
                imageView.setOnContextMenuRequested(event -> showPopUp(e, event));
                root.getChildren().add(imageView);
                if (!root.getChildren().contains(imageView)) {
                    root.getChildren().add(imageView);
                }
            }
            if (e.hasComponents(HUD.class, Position.class)) {
                root.getChildren().add(HUDupdateUtility.updateHUD(e, myResources));
            }
        }
    }

    private BorderPane createMainBorderPane (Group root, SubScene subScene) {
        BorderPane pane = new BorderPane();
        ScrollPane center = new ScrollPane();
        root.setManaged(false);
        double gapSize = 1;
        pane.setPadding(new Insets(gapSize, gapSize, gapSize, gapSize));
        pane.setCenter(center);
        center.setContent(subScene);
        center.setVbarPolicy(ScrollBarPolicy.NEVER);
        center.setHbarPolicy(ScrollBarPolicy.NEVER);
        pane.setBottom(setUpInputPane());
        return pane;
    }

    private BorderPane setUpInputPane () {
        BorderPane pane = new BorderPane();
        if (debug) {
            pane.setTop(console);
        }
        pane.setBottom(buttonBox);
        return pane;
    }

    private void initButtons () {
        if (debug) {
            buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("evaluate"), e -> this.evaluate()));
            buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("loopManager"), e -> this.createLoopManager()));
        }else{
        	buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("mainMenu"), e -> ToMainMenu.toMainMenu(pane)));
        }
        
        buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("startGameLoop"), e -> this.model.play()));
        buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("pauseGameLoop"), e -> this.model.pauseLoop()));
    }

    private void initConsole () {
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

    public void showPopUp (IEntity entity, ContextMenuEvent event) {

        Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<>();
        menuMap.put(myResources.getString("remove"), e -> EnvironmentHelperMethods.removeFromDisplay(entity, getEntitySystem()));
        menuMap.put(myResources.getString("sendBack"), e -> EnvironmentHelperMethods.sendToBack(entity, getEntitySystem()));
        menuMap.put(myResources.getString("sendFront"), e -> EnvironmentHelperMethods.sendToFront(entity, getEntitySystem()));
        menuMap.put(myResources.getString("sendBackOne"), e -> EnvironmentHelperMethods.sendBackward(entity, getEntitySystem()));
        menuMap.put(myResources.getString("sendForwardOne"), e -> EnvironmentHelperMethods.sendForward(entity, getEntitySystem()));

        PopUp myPopUp = new PopUp(GUISize.POP_UP_WIDTH.getSize(), GUISize.POP_UP_HIEGHT.getSize());
        myPopUp.show(setPopUp(menuMap), event.getScreenX(), event.getScreenY());
        myPopUpList.add(myPopUp);
    }

    public ScrollPane setPopUp (Map<String, EventHandler<ActionEvent>> map) {
        VBox box = new VBox();
        for (Entry<String, EventHandler<ActionEvent>> entry : map.entrySet()) {
            Button button = ButtonFactory.makeButton(entry.getKey(), entry.getValue());
            button.setMaxWidth(Double.MAX_VALUE);
            box.getChildren().add(button);
        }
        return new ScrollPane(box);
    }

    private void evaluate () {
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

    public void dehighlight (IEntity entity) {
        EnvironmentHelperMethods.dehighlight(entity);
    }

}