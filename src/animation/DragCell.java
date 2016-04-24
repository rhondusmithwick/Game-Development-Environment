package animation;

import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import java.util.*;

	public class DragCell extends ListCell<String> {
		
		ListCell thisCell;
		
		public DragCell(){
			thisCell = this;
			setActions();
		}

		private void setActions() {
			setAlignment(Pos.CENTER);
			setOnDragDetected(event -> dragged(event, this));
			setOnDragOver(event -> dragOver(event, this));
			setOnDragEntered(event -> dragEntered(event, this));
			setOnDragExited(event -> dragExited(event, this));
			setOnDragDropped(event -> dragDone(event, this));
			setOnDragDone(DragEvent::consume);
		}

		private void dragDone(DragEvent event, DragCell dragCell) {
			if (getItem() == null) {
				return;
			}

			Dragboard db = event.getDragboard();
			boolean success = false;

			if (db.hasString()) {
				ObservableList<String> items = getListView().getItems();
				int draggedIndex = items.indexOf(db.getString());
				int thisIndex = items.indexOf(getItem());

				items.set(draggedIndex, getItem());
				items.set(thisIndex, db.getString());

				List<String> itemscopy = new ArrayList<>(getListView().getItems());
				getListView().getItems().setAll(itemscopy);

				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
		}

		private void dragExited(DragEvent event, DragCell cell) {
			if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
				setOpacity(1);
			}
		}

		private void dragEntered(DragEvent event, DragCell cell) {
			if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
				setOpacity(0.3);
			}
		}

		private void dragOver(DragEvent event, DragCell cell) {
			if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		}

		private void dragged(MouseEvent event, DragCell dragCell) {
			if (getItem() == null) {
				return;
			}

			//ObservableList<String> items = getListView().getItems();

			Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
			ClipboardContent content = new ClipboardContent();
			content.putString(getItem());
			dragboard.setContent(content);
			// dragboard.setDragView(
			// mainList.get(getListView().getItems().indexOf(this))
			// );

			event.consume();
			return;
		}
	}
