package view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.enums.GUISize;
import java.util.List;

/**
 * abstract class which acts as a base for any pop ups shown by the view.
 *
 * @author Cali
 */
public abstract class PopUp {

    private final int height;
    private final int width;
    private Stage stage;
    private Scene myScene;
    private Group root;
    private VBox vBox;

    /**
     * Super constructor for a popup subclass instance
     *
     * @param height          int height of window
     * @param width           int width of window
     * @param backgroundColor css string for window background color
     */
    protected PopUp(int width, int height) {
        this.height = height;
        this.width = width;
    }

    /**
     * creates the popup and its various components then shows the window
     */
    public void show() {
        stage = new Stage();
        root = new Group();
        myScene = new Scene(root, width, height);
        createContainer();
        createScene();
        showScene();
    }

    /**
     * creates the scene to be shown in the popup body
     */
    protected abstract void createScene();


    private void createContainer() {
        vBox = new VBox(GUISize.POP_UP_PADDING.getSize());
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.prefHeightProperty().bind(myScene.heightProperty());
        vBox.prefWidthProperty().bind(myScene.widthProperty());
        root.getChildren().add(vBox);

    }

    /**
     * shows the scene to the user
     */
    private void showScene() {
        stage.setScene(myScene);
        stage.show();

    }

    /**
     * closes the popup
     */
    protected void closeScene() {
        stage.close();
    }

    /**
     * adds nodes to the popups scene in order
     *
     * @param nodeList list of nodes to be added
     */
    protected void addNodes(List<Node> nodeList) {
        vBox.getChildren().addAll(nodeList);
    }

    /**
     * gets the size property of the popups created scene
     *
     * @param height boolean whether to return height or width
     * @return height or width property of scene
     */
    protected ReadOnlyDoubleProperty getSize(boolean height) {
        if (height) {
            return myScene.heightProperty();
        }
        return myScene.widthProperty();
    }

    /**
     * sets the popup stage's title
     *
     * @param title string title to set
     */
    protected void setStageTitle(String title) {
        stage.setTitle(title);
    }

    /**
     * hides the popups stage from the user
     */
    protected void hideScene() {
        stage.hide();
    }

}
