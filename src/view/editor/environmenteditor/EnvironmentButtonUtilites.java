package view.editor.environmenteditor;

import api.IEntity;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.editor.EditorFactory;
import view.editor.entityeditor.EditorEntity;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.ContextMenuFactory;
import view.utilities.EntityCopier;
import view.utilities.PopUp;
import voogasalad.util.reflection.Reflection;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Bruna
 */
public class EnvironmentButtonUtilites {

    private final EditorEnvironment myControl;
    private final String myLanguage;

    EnvironmentButtonUtilites (EditorEnvironment control, String language) {
        myLanguage = language;
        myControl = control;
    }
    
    public HBox makeMainButtons(HBox box){
    	Map<String, EventHandler<ActionEvent>> buttonMap  = myControl.makeButtonMap();
        for (Entry<String, EventHandler<ActionEvent>> entry : buttonMap.entrySet()) {
            box.getChildren().add(ButtonFactory.makeButton(entry.getKey(), entry.getValue()));
        }
    		return box;
    }

    public void populateVbox (VBox vbox, Collection<IEntity> collection, String methodName) {
        vbox.getChildren().clear();
        for (IEntity entity : collection) {
            Button button = (Button) Reflection.callMethod(this, methodName, entity);
            (button).setMaxWidth(Double.MAX_VALUE);
            vbox.getChildren().add(button);
        }
    }

    public Button createAddEntityButton (IEntity entity) {
        return ButtonFactory.makeButton((entity).getName(), e -> myControl.addEntityToBoth(EntityCopier.copyEntity(entity)));
    }

    public Button createEntityButton (IEntity entity) {
        Button entityInButton = new Button(entity.getName());
        entityInButton.setMaxWidth(Double.MAX_VALUE);
        entityInButton.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();
            if (button == MouseButton.PRIMARY) {
                entityLeftClicked(entity);
            } else if (button == MouseButton.SECONDARY) {
                entityRightClicked(entity, entityInButton, event);
            }
        });
        entityInButton.setOnMouseEntered(e -> EnvironmentHelperMethods.highlight(entity));
        entityInButton.setOnMouseExited(e -> EnvironmentHelperMethods.dehighlight(entity));
        return entityInButton;
    }

    private void entityLeftClicked (IEntity entity) {
        ObservableList<IEntity> entityList = FXCollections.observableArrayList();
        EditorEntity entityEditor = (EditorEntity) new EditorFactory().createEditor(EditorEntity.class.getName(),
                myLanguage, entity, entityList);
        entityEditor.populateLayout();
        entityList.addListener((ListChangeListener<IEntity>) c -> myControl.updateEditor());
        PopUp myPopUp = new PopUp(GUISize.ENTITY_EDITOR_WIDTH.getSize(), GUISize.ENTITY_EDITOR_HEIGHT.getSize());
        myPopUp.show(entityEditor.getPane());
    }

    private void entityRightClicked (IEntity entity, Button entityButton, MouseEvent event) {
        entityButton.setContextMenu(ContextMenuFactory.createContextMenu(myControl.makeMenuMap(entity, entityButton, event)));
    }

}
