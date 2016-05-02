package view.utilities;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;

public class TitledPaneFactory {
    private TitledPaneFactory () {

    }

    /**
     * Creates a Titled Pane, with all content already inside.
     *
     * @param String  title: Title that will appear on the top of the Pane
     * @param Node    content: Content to be added. It can be a Group or a VBox or
     *                HBox.
     * @param boolean collapsable: If it's collapsable, the pane will not be
     *                expanded. If it isn't, it will.
     * @return TitledPane pane, already initialized.
     */

    public static TitledPane makeTitledPane (String title, Node content, boolean collapsable) {
        TitledPane pane = new TitledPane(title, content);
        pane.setCollapsible(collapsable);
        pane.setExpanded(!collapsable);
        return pane;
    }
}
