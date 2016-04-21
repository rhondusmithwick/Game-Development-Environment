package view.editor.gameeditor;

import api.IEditor;
import api.IEntity;
import api.ISerializable;
import enums.GUISize;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import view.Authoring;
import view.editor.EditorFactory;

public abstract class ObjectDisplay {
	
	private final EditorFactory editFact = new EditorFactory();
	private String language;
	private Authoring authEnv;
	private ObservableList<IEntity> masterEntityList;
	private VBox container;
	
	
	public ObjectDisplay(String language, Authoring authEnv, ObservableList<IEntity> masterEntityList){
		this.language = language;
		this.authEnv=authEnv;
		this.masterEntityList=masterEntityList;
	}
	
	
	public ScrollPane init() {
		ScrollPane scroll = new ScrollPane();
		container = new VBox(GUISize.SCROLL_PAD.getSize());
		updateDisplay();
		scroll.setContent(container);
		return scroll;
	}
	
	
	protected void addListeners(ObservableList<ISerializable> toObserve) {
		toObserve.addListener(new ListChangeListener<ISerializable>() {
			@Override
			public void onChanged(@SuppressWarnings("rawtypes") ListChangeListener.Change change) {
				updateDisplay();
			}
		});
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void createEditor(Class<?> editName, ISerializable toEdit, ObservableList<ISerializable> otherList) {
		IEditor editor = editFact.createEditor(editName, language, toEdit, (ObservableList<ISerializable>) ((ObservableList<?>) masterEntityList), otherList);
		editor.populateLayout();
		authEnv.createTab(editor.getPane(), editName.getSimpleName(), true);
	}
	
	protected void updateDisplay(){
		container.getChildren().clear();;
		addNewObjects(container);
		
	}


	protected abstract void addNewObjects(VBox container);
	
	public abstract Node makeNewObject();

}
