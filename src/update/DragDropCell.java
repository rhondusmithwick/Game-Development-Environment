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
	private DataFormat dataFormat = new DataFormat();

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
			Object dropObject = e.getDragboard().getContent(dataFormat);
			T test = (T) dropObject;
			ObservableList<T> list = getListView().getItems();
			
			int oldIndex = list.indexOf(dropObject);
			int newIndex = getIndex();
			list.set(newIndex, list.get(oldIndex));
			list.set(oldIndex, getItem());
			getListView().getItems().addAll(list);
			
			
			
		});
	}
	
	private boolean canDrop(DragEvent e, DataFormat dataFormat) {
		
		return e.getDragboard().hasContent(dataFormat) && e.getGestureSource() != this;
	}
}
