// This entire file is part of my masterpiece.
// Alan Cavalcanti
package view.editor.eventeditor.tabs;

import api.IEntity;
import api.ILevel;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import view.editor.eventeditor.tables.EventViewManager;
import view.editor.eventeditor.tables.TabWithViewerManager;
import view.enums.GUISize;
import view.enums.ViewInsets;
import view.utilities.ButtonFactory;
import view.utilities.TextFieldFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Event Editor pane that contains an Entity navigator, and text field to input time.
 * Extends the EventEditorWithViewer for it contains the navigator, and a very simple
 * trigger input (Time String)
 * @author Alankmc
 *
 */

public class TimeEventEditor extends EventEditorWithViewer {
    private static final int FONT_SIZE = 20;
	private final ScrollPane scrollPane;
    private final VBox pane;
    private final ResourceBundle myResources;
    private final TextField textField;
    private Text chosenEntityText;
    private static final String TRIGGER_TYPE = "TimeTrigger";

    public TimeEventEditor (String language, ObservableList<ILevel> levelList) {
        super(language, levelList, TRIGGER_TYPE);
        
        myResources = ResourceBundle.getBundle(language);
        scrollPane = new ScrollPane();

        pane = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        pane.setPadding(ViewInsets.GAME_EDIT.getInset());
        pane.setAlignment(Pos.TOP_LEFT);

        addParametersPane(pane);

        choseLevels(new ArrayList<>(levelList));
        textField = TextFieldFactory.makeTextArea(myResources.getString("inputTime"));
        getCreateEventButton().setOnAction(e -> getTime());
        populateLayout();
    }

    /** 
     * Create Event button handler. Will take in the Time String from the textField
     * and call the create Event method from super.
     */
    private void getTime()
    {
        double time;

        try {
            time = Double.parseDouble(textField.getText());
        } catch (Exception e) {
            return;
        }
        createEvent(time);
    }

    /**
     * Populates the upper side of the layout. Contains the input keys, and the 
     * Entity navigator.
     */
    public void makeUpperSide () {
        HBox container = new HBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        VBox innerContainer = new VBox(GUISize.EVENT_EDITOR_SUBPADDING.getSize());

        innerContainer.getChildren().addAll(textField, getActionPane(), getCreateEventButton());

        chosenEntityText = new Text();

        container.getChildren().addAll(getLevelPickerPane(), getTable(), getEntityPickerPane(), innerContainer);

        pane.getChildren().add(container);
    }


    public void makeBottomSide () {
        pane.getChildren().add(getEventViewer());
    }
    
    /**
     * Make bottom side of the UI. Contains the Event Viewer.
     */
    @Override
    public ScrollPane getPane () {
        return scrollPane;
    }

    @Override
    public void populateLayout () {
        makeUpperSide();
        makeBottomSide();

        scrollPane.setContent(pane);
    }
    @Override
    public void updateEditor () {
    }

    
}
