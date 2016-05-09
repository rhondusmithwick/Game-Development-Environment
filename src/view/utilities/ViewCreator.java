package view.utilities;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import view.ConsoleTextArea;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Tom on 5/8/2016.
 */
public class ViewCreator {

    public static BorderPane createMainBorderPane (SubScene subScene, ResourceBundle viewProperties,
                                             boolean editingOn, ConsoleTextArea console, HBox buttonBox) {
        BorderPane pane = new BorderPane();
        ScrollPane center = new ScrollPane();
        center.setContent(subScene);
        center.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        center.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        double gapSize;
        try {
            gapSize = Double.parseDouble(viewProperties.getString("GapSize"));
        } catch (NumberFormatException e) {
            gapSize = 1; // DEFAULT
        }
        pane.setPadding(new Insets(gapSize, gapSize, gapSize, gapSize));
        pane.setCenter(center);
        pane.setBottom(setUpInputPane(editingOn, console, buttonBox));
        return pane;
    }

    private static BorderPane setUpInputPane (boolean editingOn, ConsoleTextArea console, HBox buttonBox) {
        BorderPane pane = new BorderPane();
        if (editingOn) {
            pane.setTop(console);
        }
        pane.setBottom(buttonBox);
        return pane;
    }

    public static ConsoleTextArea initConsole (ResourceBundle myResources, EventHandler<KeyEvent> eh) {
        ConsoleTextArea console = new ConsoleTextArea();
        console.setText(myResources.getString("enterCommands"));
        console.appendText("\n\n");

        console.setOnKeyPressed(eh);
        return console;
    }

    public static HBox initButtons (ResourceBundle myResources, Map<String, EventHandler<ActionEvent>> map) {
        HBox buttonBox = new HBox();
//        if (editingOn) {
//            buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("evaluate"), e -> this.evaluate()));
//            buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("loopManager"), e -> manager.show()));
//        } else {
//            buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("mainMenu"), e -> ToMainMenu.toMainMenu(pane)));
//        }
//
//        buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("startGameLoop"), e -> this.model.play()));
//        buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString("pauseGameLoop"), e -> this.model.pauseLoop()));
        for(String key:map.keySet()) {
            buttonBox.getChildren().add(ButtonFactory.makeButton(myResources.getString(key), map.get(key)));
        }
        return buttonBox;
    }

}
