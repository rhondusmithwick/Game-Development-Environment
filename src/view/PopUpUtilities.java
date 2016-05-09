package view;

import api.IEntity;
import api.IEntitySystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.PopUp;

import java.util.*;

/**
 * Created by Bruna, refactored by Tom.
 */
public class PopUpUtilities {
    private final List<PopUp> myPopUpList = new ArrayList<>();

    public PopUpUtilities() {
    }

    public void showPopUp (IEntity entity, ContextMenuEvent event, ResourceBundle myResources, IEntitySystem myEntitySystem) {

        Map<String, EventHandler<ActionEvent>> menuMap = new LinkedHashMap<>();
        menuMap.put(myResources.getString("remove"), e -> ViewFeatureMethods.removeFromDisplay(entity, myEntitySystem));
        menuMap.put(myResources.getString("sendBack"), e -> ViewFeatureMethods.sendToBack(entity, myEntitySystem));
        menuMap.put(myResources.getString("sendFront"), e -> ViewFeatureMethods.sendToFront(entity, myEntitySystem));
        menuMap.put(myResources.getString("sendBackOne"), e -> ViewFeatureMethods.sendBackward(entity, myEntitySystem));
        menuMap.put(myResources.getString("sendForwardOne"), e -> ViewFeatureMethods.sendForward(entity, myEntitySystem));

        PopUp myPopUp = new PopUp(GUISize.POP_UP_WIDTH.getSize(), GUISize.POP_UP_HIEGHT.getSize());
        myPopUp.show(setPopUp(menuMap), event.getScreenX(), event.getScreenY());
        myPopUpList.add(myPopUp);
    }

    public void deletePopUps (MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            myPopUpList.stream().forEach(PopUp::closeScene);
            myPopUpList.clear();
        }
    }

    public ScrollPane setPopUp (Map<String, EventHandler<ActionEvent>> map) {
        VBox box = new VBox();
        for (Map.Entry<String, EventHandler<ActionEvent>> entry : map.entrySet()) {
            Button button = ButtonFactory.makeButton(entry.getKey(), entry.getValue());
            button.setMaxWidth(Double.MAX_VALUE);
            box.getChildren().add(button);
        }
        return new ScrollPane(box);
    }
}
