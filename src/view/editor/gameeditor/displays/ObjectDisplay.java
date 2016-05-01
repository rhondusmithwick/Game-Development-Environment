package view.editor.gameeditor.displays;

import api.IEditor;
import api.ISerializable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import view.Authoring;
import view.editor.EditorFactory;
import view.enums.GUISize;

public abstract class ObjectDisplay {

    private final EditorFactory editFact = new EditorFactory();
    private Authoring authEnv;
    private VBox container;


    public ObjectDisplay (Authoring authEnv) {
        this.authEnv = authEnv;
    }


    public ScrollPane init () {
        ScrollPane scroll = new ScrollPane();
        container = new VBox(GUISize.SCROLL_PAD.getSize());
        updateDisplay();
        scroll.setContent(container);
        return scroll;
    }


    protected void addListeners (ObservableList<ISerializable> toObserve) {
        toObserve.addListener(new ListChangeListener<ISerializable>() {
            @Override
            public void onChanged (@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
                updateDisplay();
            }
        });

    }


    public void createEditor (String name, Object... args) {
        IEditor editor = editFact.createEditor(name, args);
        editor.populateLayout();
        authEnv.createTab(editor.getPane(), name.substring(name.lastIndexOf('.') + 1), true);
    }

    protected void updateDisplay () {
        container.getChildren().clear();
        ;
        addNewObjects(container);

    }


    protected abstract void addNewObjects (VBox container);

    public abstract Node makeNewObject ();

}
