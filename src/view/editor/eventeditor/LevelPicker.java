package view.editor.eventeditor;

import api.ILevel;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import view.editor.eventeditor.tabs.EventEditorTab;
import view.enums.GUISize;
import view.enums.ViewInsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Contains a pane full of Checkboxes, that lets the user choose an amount of previously created Levels.
 * These are put into a List, and can be used by any Pane that has an instance of the Level Picker.
 *
 * @author Alankmc
 */
public class LevelPicker {
    private final HashMap<CheckBox, ILevel> checkBoxMap;
    private final EventEditorTab eventAuthoring;
    private final ObservableList<ILevel> levelList;
    private final ScrollPane pane;
    private final VBox vbox;
    private final ArrayList<ILevel> selectedLevels;
    private final ResourceBundle myResources;
    private String language;
    private CheckBox allBox;

    /**
     * Constructor. Takes in any EventAuthoringTab as the authoring Pane that contains this Level Picker.
     *
     * @param String                 language
     * @param ObservableList<ILevel> levelList
     * @param EventAuthoringTab      eventAuthoring
     */

    public LevelPicker (String language, ObservableList<ILevel> levelList, EventEditorTab eventAuthoring) {
        checkBoxMap = new HashMap<>();
        this.eventAuthoring = eventAuthoring;
        this.levelList = levelList;
        selectedLevels = new ArrayList<>();
        myResources = ResourceBundle.getBundle(language);

        vbox = new VBox(GUISize.EVENT_EDITOR_PADDING.getSize());
        vbox.setPadding(ViewInsets.GAME_EDIT.getInset());
        vbox.setAlignment(Pos.TOP_LEFT);

        populatePane();
        pane = new ScrollPane(vbox);
    }

    /**
     * Creates all visual components.
     */
    private void populatePane () {
        allBox = new CheckBox(myResources.getString("all"));
        allBox.setSelected(true);
        allBox.setOnAction(e -> allBoxCheck(allBox.isSelected()));
        vbox.getChildren().add(allBox);

        if (levelList != null) {
            for (ILevel level : levelList) {
                CheckBox newCheckbox = new CheckBox(level.getName());
                newCheckbox.setSelected(true);
                newCheckbox.setOnAction(e -> levelCheck());
                checkBoxMap.put(newCheckbox, level);
                vbox.getChildren().add(newCheckbox);
            }
        }
    }

    /**
     * Selects/Deselects the "ALL" Checkbox, and selects/deselects all other boxes accordingly.
     *
     * @param boolean isSelected
     */
    private void allBoxCheck (boolean isSelected) {
        // System.out.println("hm.");
        for (CheckBox checkbox : checkBoxMap.keySet()) {
            checkbox.setSelected(isSelected);
        }

        levelCheck();
    }

    /**
     * Checkbox handler. Adds/removes the selected level from the selected Level list.
     */
    private void levelCheck () {
        selectedLevels.clear();
        boolean allAreSelected = true;
        // System.out.println("   === IN LEVELPICKER");
        for (CheckBox checkbox : checkBoxMap.keySet()) {
            if (checkbox.isSelected()) {
                // System.out.println("   " + checkBoxMap.get(checkbox).getName());
                selectedLevels.add(checkBoxMap.get(checkbox));
            } else {
                allAreSelected = false;
            }
        }
        // System.out.println("   ==== ENDED\n");

        allBox.setSelected(allAreSelected);
        eventAuthoring.choseLevels(selectedLevels);
    }

    /**
     * Returns the ScrollPane containing Level.
     *
     * @return ScrollPane
     */
    public ScrollPane getPane () {
        return pane;
    }
}
