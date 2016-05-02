package update;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * Create cell for drag-and-drop list.
 * 
 * @author Ben Zhang
 */
public class DragDropCell<T> extends ListCell<T> {
    public static final DataFormat dataFormat = new DataFormat("custom");

    @SuppressWarnings("unchecked")
    public DragDropCell () {
        setOnDragDetected(e -> {
            if (getItem() == null) {
                return;
            }
            Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(dataFormat, getItem());
            dragBoard.setContent(content);
        });

        setOnDragOver(e -> {
            if (canDrop(e, dataFormat)) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        setOnDragEntered(e -> {
            if (canDrop(e, dataFormat)) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(e -> {
            if (canDrop(e, dataFormat)) {
                setOpacity(1);
            }
        });

        setOnDragDropped(e -> {
            if (getItem() == null) {
                return;
            }
            Object dropObject = e.getDragboard().getContent(dataFormat);
            ObservableList<T> list = getListView().getItems();
            int oldIndex = list.indexOf(dropObject);
            int newIndex = getIndex();
            list.set(oldIndex, getItem());
            list.set(newIndex, (T) dropObject);
        });
    }

    @Override
    protected void updateItem (T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.toString());
        }
    }

    private boolean canDrop (DragEvent e, DataFormat dataFormat) {
        return e.getDragboard().hasContent(dataFormat) && e.getGestureSource() != this;
    }
}
