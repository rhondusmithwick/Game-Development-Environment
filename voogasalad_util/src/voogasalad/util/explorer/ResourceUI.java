package voogasalad.util.explorer;


import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * The class initialized by users of this utility (the entry point). It is a Tab containing the
 * TreeView structure of folders and files, with buttons to create new folders
 * and import new files.
 * 
 * @author DoovalSalad
 *
 */
public class ResourceUI extends Tab {

    /**
     * Private instance variables
     */
    private HBox buttonContainer;
    private ResourceTreeView rtv;
    private VBox container;

    /**
     * Constants
     */
    private static final String WINDOW_NAME = "Resource Explorer";
    private static final String ADD_FOLDER_PROMPT = "Add Folder";
    private static final String IMPORT_FILE_PROMPT = "Import File";
    private static final double SPACING = 10;

    /**
     * The constructor, which initializes the nodes that make up the entire UI.
     */
    public ResourceUI (String projectName) {
        container = new VBox(SPACING);
        buttonContainer = new HBox(SPACING);

        rtv = new ResourceTreeView(new VoogaFile(VoogaFileType.FOLDER, projectName));

        container.getChildren().addAll(rtv, buttonContainer);

        makeAddButtons();

        this.setText(WINDOW_NAME);
        this.setContent(container);

    }

    /**
     * Makes buttons to add folders and import files.
     */
    private void makeAddButtons () {
        ButtonMaker maker = new ButtonMaker();
        this.buttonContainer.getChildren()
                .addAll(maker.makeButton(ADD_FOLDER_PROMPT, e -> promptFolderName()),
                        maker.makeButton(IMPORT_FILE_PROMPT, e -> {
                            try {
                                importFile();
                            }
                            catch (VoogaException ee) {
                                new VoogaAlert(ee.getMessage());
                            }
                        }));
    }

    /**
     * Opens up the NamePrompter for adding folders.
     */
    private void promptFolderName () {
        new NamePrompter(rtv);
    }

    /**
     * Opens the FileImporter for importing files.
     */
    private void importFile () throws VoogaException {
        new FileImporter(rtv);
    }

    public ResourceTreeView getRTV () {
        return this.rtv;
    }

}
