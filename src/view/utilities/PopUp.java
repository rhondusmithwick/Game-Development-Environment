package view.utilities;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

/**
 * abstract class which acts as a base for any pop ups shown by the view.
 *
 * @author Cali
 */
public class PopUp {

    private final double height;
    private final double width;
    private Stage stage;
    private Scene myScene;
    private VBox vBox;

    /**
     * Super constructor for a popup subclass instance
     *
     * @param e          int height of window
     * @param d           int width of window
     * @param backgroundColor css string for window background color
     */
    public PopUp(double d, double e) {
        this.height = e;
        this.width = d;
    }

    /**
     * creates the popup and its various components then shows the window
     */
    public void show(ScrollPane pane) {
        stage = new Stage();
        myScene = new Scene(pane, width, height);
        showScene();
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
     * hides the popups stage from the user
     */
    protected void hideScene() {
        stage.hide();
    }

}
