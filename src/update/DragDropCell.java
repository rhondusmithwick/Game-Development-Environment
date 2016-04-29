package update;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class DragDropCell<T> extends ListCell<T> {

	//private ObservableList<T> list;
	public static final DataFormat dataFormat = new DataFormat("custom");

	public DragDropCell() {
		//System.out.println("hi");
		//list = getListView().getItems();
		//DataFormat dataFormat = new DataFormat("sdsd");
		//int newIndex = 0;

		setOnDragDetected(e -> {
			if(getItem() == null) {
				return;
			}

			Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.put(dataFormat, getItem().toString());
			dragBoard.setContent(content);
		});

		setOnDragOver(e -> {
			//System.out.println("over");
			if(canDrop(e, dataFormat)) {
				//newIndex = getIndex();
				e.acceptTransferModes(TransferMode.MOVE);
			}
		});

		setOnDragEntered(e -> {
			if(canDrop(e, dataFormat)) {
				setOpacity(0.3);
			}
		});

		setOnDragExited(e -> {
			if(canDrop(e, dataFormat)) {
				setOpacity(1);
			}
		});

		setOnDragDropped(e -> {
			if (getItem() == null) {
                return;
            }
			Object dropObject = e.getDragboard().getContent(dataFormat);
			T test = (T) dropObject;
			ObservableList<T> list = getListView().getItems();
			System.out.println(list);
			int oldIndex = list.indexOf(dropObject);
			int newIndex = getIndex();
			System.out.println(oldIndex);
			System.out.println(newIndex);
			list.set(newIndex, list.get(oldIndex));
			list.set(oldIndex, getItem());
			//System.out.println(getItem().toString());
			System.out.println(list);
			//getListView().getItems().setAll(list);



		});
	}

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
			setText(item.toString());
		}
	}


	private boolean canDrop(DragEvent e, DataFormat dataFormat) {
		return e.getDragboard().hasContent(dataFormat) && e.getGestureSource() != this;
	}
}
