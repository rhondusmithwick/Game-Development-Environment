package view.editor.eventeditor.tables;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.enums.GUISize;

import java.lang.reflect.Method;

/**
 * Abstract Table class. It contains a single Column, and takes in an abstract Entry.
 *
 * @author Alankmc
 */
public abstract class Table {
    private TableView<Entry> table;
    private TableColumn<Entry, String> column;
    private ObservableList<Entry> entries;
    private TableManager manager;

    /**
     * Constructor.
     *
     * @param TableManager manager
     *                     Manager that controls what ever happens to the table.
     * @param String       name
     *                     The table's name, to be displayed on the top.
     * @param Method       clickHandler
     *                     Method object, that will be used as a handler to whatever happens when the table is clicked.
     *                     It invokes the manager's method, casts the Table Data to the handlerArgumentClass, and passes
     *                     it as an argument.
     * @param Class        handlerArgumentClass
     *                     Class to cast the Table data, when the handler is fired.
     */
    public Table (TableManager manager, String name, Method clickHandler, Class handlerArgumentClass) {
        this.manager = manager;
        table = new TableView<Entry>();
        table.setEditable(true);
        table.setPrefWidth(GUISize.EVENT_EDITOR_TABLE_WIDTH.getSize());
        table.setMaxHeight(250);    // TODO magic value

        if (clickHandler != null) {
            table.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) ->
                    {
                        try {
                            clickHandler.invoke(manager, handlerArgumentClass.cast(observableValue.getValue().getData()));
                        } catch (Exception e) {
                            // TODO BAAAAAAD
                        }
                    }
            );
        }

        column = new TableColumn<Entry, String>(name);
        column.setCellValueFactory(new PropertyValueFactory<Entry, String>("name"));
        column.minWidthProperty().bind(table.prefWidthProperty());
        column.maxWidthProperty().bind(table.prefWidthProperty());
        column.setSortable(false);

        entries = FXCollections.observableArrayList();
        table.getColumns().add(column);
        table.setItems(entries);
    }

    /**
     * Abstract method to fill entries. Each Table fills this in differently.
     *
     * @param Object dataHolder
     *               Usually is some Collection or subclass thereof. Will be iterated through
     *               on subclasses of Table.
     */
    public abstract void fillEntries (Object dataHolder);

    /**
     * Gets manager.
     *
     * @return TableManager
     */
    public TableManager getManager () {
        return manager;
    }

    /**
     * Gets table.
     *
     * @return TableView<Entry>
     */
    public TableView<Entry> getTable () {
        return table;
    }

    /**
     * Gets the entries that the table is observing.
     *
     * @return ObservableList<Entry>
     */
    public ObservableList<Entry> getEntries () {
        return entries;
    }

    /**
     * Clears the table and the observable list.
     */
    public void refreshTable () {
        entries.clear();
        table.refresh();
    }
}
