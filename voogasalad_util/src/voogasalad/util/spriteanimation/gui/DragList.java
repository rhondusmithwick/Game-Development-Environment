package voogasalad.util.spriteanimation.gui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

@Deprecated
public class DragList {

    private ObservableList<Button> buttons;

    private DragList(ObservableList<Button> theButtons) {
        buttons = theButtons;
        setActions();
    }

    public static void makeIntoDragList(ObservableList<Button> theButtons) {
        new DragList(theButtons);
    }

    private void setActions() {
        for (Button button : buttons) {
            button.setAlignment(Pos.CENTER);
            button.setOnDragDetected(event -> dragged(event, button));
            button.setOnDragOver(event -> dragOver(event, button));
            button.setOnDragEntered(event -> dragEntered(event, button));
            button.setOnDragExited(event -> dragExited(event, button));
            button.setOnDragDropped(event -> dragDone(event, button));
            button.setOnDragDone(DragEvent::consume);
        }
    }

    private void dragDone(DragEvent event, Button button) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            int thisIndex = buttons.indexOf(button);
            Button movedButton = null;
            for (Button dragButton : buttons) {
                if (dragButton.getText().equals(db.getString())) {
                    movedButton = dragButton;
                }
            }
            buttons.remove(movedButton);
            buttons.add(thisIndex, movedButton);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void dragExited(DragEvent event, Button button) {
        if (event.getGestureSource() != button && event.getDragboard().hasString()) {
            button.setOpacity(1);
        }
    }

    private void dragEntered(DragEvent event, Button button) {
        if (event.getGestureSource() != button && event.getDragboard().hasString()) {
            button.setOpacity(0.3);
        }
    }

    private void dragOver(DragEvent event, Button button) {
        if (event.getGestureSource() != button && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    private void dragged(MouseEvent event, Button button) {
        Dragboard db = button.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        System.out.println("button text" + button.getText());
        content.putString(button.getText());
        db.setContent(content);
        //File resource = new File("resources/images/charizard.png");
        //Image image = new Image(resource.toURI().toString());
        //db.setDragView(image);
        event.consume();
    }
}