package view.editor.eventeditor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.editor.eventeditor.tables.Entry;
import view.editor.eventeditor.tables.TableManager;
import view.enums.GUISize;

public abstract class Table {
    private final TableView<Entry> table;
    private final ObservableList<Entry> entries;
    private final TableManager manager;

    public Table (TableManager manager, String name) {
        this.manager = manager;
        table = new TableView<>();
        table.setEditable(true);
        table.setPrefWidth(GUISize.EVENT_EDITOR_TABLE_WIDTH.getSize());
        table.setMaxHeight(250);    // TODO magic value
        TableColumn<Entry, String> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>("name"));
        column.minWidthProperty().bind(table.prefWidthProperty());
        column.maxWidthProperty().bind(table.prefWidthProperty());

        entries = FXCollections.observableArrayList();
        table.getColumns().add(column);
        table.setItems(entries);
    }

    public abstract void fillEntries (Object dataHolder);

    public TableManager getManager () {
        return manager;
    }

    public TableView<Entry> getTable () {
        return table;
    }

    public ObservableList<Entry> getEntries () {
        return entries;
    }

    public void refreshTable () {
        entries.clear();
        table.refresh();
    }
}
