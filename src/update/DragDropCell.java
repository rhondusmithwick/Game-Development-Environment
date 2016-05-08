// This entire file is part of my masterpiece.
// BEN ZHANG
/*
 * I think that this class demonstrates my progress in this class over the course of the semester.
 * It allows one to override the default behavior of a cell in a ListView object, allowing me to 
 * implement drag-and-drop re-ordering functionality for the list displayed in said ListView. 
 * This class makes use of many of the concepts we have learned over the course of the semester.
 * These include some of the advanced Java features we discussed in lecture, such as lambda
 * expressions and generics. The latter is especially interesting. It enables this class to be used
 * by a ListView of ANY data type, not just the String type. Thus, it can be implemented in any
 * project that includes instances of ListViews, meaning that it has excellent flexibility.
 * To improve this class, I refactored it to use constants instead of magic numbers and extracted
 * some code to separate methods for additional readability and flexibility. All methods contain
 * twenty or fewer lines of code, and the class as a whole contains under 200 lines of code, both of
 * which are desirable outcomes.
 * To demonstrate that this class can be extended by ListViews of different types, I have modified
 * the Game Loop Manager class to use ListViews of type Integer, as opposed to String. You can see 
 * for yourself that it still functions as before: After running the Main class, go to Make Game -> 
 * Create Environment, scroll down, and click on Loop Manager. Enter values as desired and enjoy.
 * Don't forget to hit Save Metadata when you are done entering vales and Save Environment back in
 * the environment editor.
 */
package update;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

/**
 * Create custom cell for drag-and-drop list.
 * 
 * @author Ben Zhang
 */
public class DragDropCell<T> extends ListCell<T> {
	private static final DataFormat DATA_FORMAT = new DataFormat("custom");
	private static final double DRAG_ENTERED = 0.3;
	private static final double DRAG_EXITED = 1.0;

	public DragDropCell () {
		setDragEvents();
	}

	private void setDragEvents () {
		setOnDragDetected(e -> dragDetected());
		setOnDragOver(e -> dragOver(e));
		setOnDragEntered(e -> dragEnterExit(e, true));
		setOnDragExited(e -> dragEnterExit(e, false));
		setOnDragDropped(e -> dragDropped(e));
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

	private void dragDetected () {
		if (getItem() == null) {
			return;
		}
		Dragboard dragBoard = startDragAndDrop(TransferMode.MOVE);
		ClipboardContent content = new ClipboardContent();
		content.put(DATA_FORMAT, getItem());
		dragBoard.setContent(content);
	}

	private void dragOver (DragEvent e) {
		if (canDrop(e, DATA_FORMAT)) {
			e.acceptTransferModes(TransferMode.MOVE);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void dragDropped (DragEvent e) {
		if (getItem() == null) {
			return;
		}
		Object dropObject = e.getDragboard().getContent(DATA_FORMAT);
		ObservableList<T> list = getListView().getItems();
		int oldIndex = list.indexOf(dropObject);
		int newIndex = getIndex();
		list.set(oldIndex, getItem());
		list.set(newIndex, (T) dropObject);
	}
	
	private void dragEnterExit (DragEvent e, boolean enter) {
		if(enter) {
			setOpacity(DRAG_ENTERED);
		} else {
			setOpacity(DRAG_EXITED);
		}
	}

	private boolean canDrop (DragEvent e, DataFormat dataFormat) {
		return e.getDragboard().hasContent(dataFormat) && e.getGestureSource() != this;
	}
}
