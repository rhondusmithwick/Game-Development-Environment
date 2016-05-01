package view.editor.environmenteditor;

import api.IEntity;
import api.ILevel;
import api.IView;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.component.movement.Position;
import model.component.visual.Sprite;
import view.View;
import view.editor.Editor;
import view.enums.DefaultEntities;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.*;

import java.io.File;
import java.util.ResourceBundle;

public class EditorEnvironment extends Editor {

	private String myLanguage;
	private BorderPane environmentPane = new BorderPane();
	private ILevel myLevel;
	private ResourceBundle myResources;
	private ObservableList<IEntity> masterEntityList;
	private ObservableList<ILevel> allEnvironmentsList;
	private VBox leftPane = new VBox();
	private VBox rightPane = new VBox();
	private VBox masterEntityButtonsBox = new VBox();
	private VBox environmentEntityButtonsBox = new VBox();
	private TextField nameField = new TextField();
	private ScrollPane scrollPane = new ScrollPane(environmentPane);
	private IView view;
	private EnvironmentUtilites myButtonsClass;

	public EditorEnvironment(String language, ILevel toEdit, ObservableList<IEntity> masterList,
			ObservableList<ILevel> addToList, Scene myScene) {
		myLanguage = language;
		myResources = ResourceBundle.getBundle(language);
		masterList.addListener((ListChangeListener<IEntity>) c -> {
			this.updateEditor();
		});
		masterEntityList = masterList;
		myLevel = toEdit;
		allEnvironmentsList = addToList;

		view = new View((GUISize.TWO_THIRDS_OF_SCREEN.getSize()), GUISize.HEIGHT_MINUS_TAB.getSize(), myLevel,
				myLanguage, myScene, true);
		myButtonsClass = new EnvironmentUtilites(view, environmentEntityButtonsBox, masterList, this, language);
		addLayoutComponents();
	}

	private void addLayoutComponents() {
		setLeftPane();
		setRightPane();
	}

	private void setLeftPane() {
		leftPane.getChildren().add(setNameDisplay());
		leftPane.getChildren().add(setEntityOptionsDisplay());
	}

	private TextField setNameDisplay() {
		if (view.getEntitySystem().getName().equals("")) {
			nameField.setText(myResources.getString("environmentName"));
		} else {
			nameField.setText(view.getEntitySystem().getName());
		}
		return nameField;
	}

	private ScrollPane setEntityOptionsDisplay() {
		if (masterEntityList.isEmpty()) {
			loadDefaults();
		}
		myButtonsClass.populateVbox(masterEntityButtonsBox, masterEntityList, "createAddEntityButton");
		return (new ScrollPane(TitledPaneFactory.makeTitledPane(myResources.getString("masterTemplates"), masterEntityButtonsBox, true)));
	}

	void addToSystem(IEntity entity) {
		String newName = UserInputBoxFactory.userInputBox(myResources.getString("noName"),
				myResources.getString("addEntityName"));
		if (newName != null) {
			entity.setName(newName);
		}
		view.getEntitySystem().addEntity(entity);
		try {
			if (!entity.hasComponent(Position.class) || !entity.hasComponent(Sprite.class)) {
				addComponents(entity);
			}
			environmentEntityButtonsBox.getChildren().add(myButtonsClass.createEntityButton(entity));
		} catch (Exception e) {
			Alerts.showAlert(myResources.getString("error"), null, myResources.getString("unableToAddEntity"),
					AlertType.ERROR);
		}
	}

	private void loadDefaults() {
		if (Alerts.showAlert(myResources.getString("addDefaults"), myResources.getString("addDefaultsQuestion"),
				myResources.getString("defaultsMessage"), AlertType.CONFIRMATION)) {
			masterEntityList.add(DefaultEntities.BACKGROUND.getDefault());
			masterEntityList.add(DefaultEntities.CHAR_1.getDefault());
			masterEntityList.add(DefaultEntities.CHAR_2.getDefault());
		}
	}

	private void setRightPane() {
		rightPane.getChildren()
				.add(ButtonFactory.makeButton(myResources.getString("saveEnvironment"), e -> saveEnvironment()));
		rightPane.getChildren().add(new ScrollPane(TitledPaneFactory.makeTitledPane(myResources.getString("environmentInstances"), environmentEntityButtonsBox, true)));
	}

	@Override
	public void updateEditor() {
		myButtonsClass.populateVbox(masterEntityButtonsBox, masterEntityList, "createAddEntityButton");
		myButtonsClass.populateVbox(environmentEntityButtonsBox, view.getLevel().getAllEntities(), "createEntityButton");
	}

	@Override
	public void populateLayout() {
		environmentPane.setRight(rightPane);
		environmentPane.setLeft(leftPane);
		environmentPane.setCenter(view.getPane());
	}

	private void saveEnvironment() {
		String name = getName();
		view.getEntitySystem().setName(name);
		allEnvironmentsList.remove(view.getLevel());
		allEnvironmentsList.add(view.getLevel());
		environmentPane.getChildren().clear();
		environmentPane.setCenter(saveMessage(myResources.getString("saveMessage")));
	}

	private String getName() {
		String returnName = null;
		if (nameField.getText().equals(myResources.getString("environmentName"))) {
			returnName = UserInputBoxFactory.userInputBox(myResources.getString("noName"),
					myResources.getString("noNameMessage"));
		} else {
			returnName = nameField.getText();
		}
		return returnName;
	}

	private void addComponents(IEntity entity) {
		if (Alerts.showAlert(myResources.getString("confirm"), myResources.getString("componentsRequired"),
				myResources.getString("addComponentQuestion"), AlertType.CONFIRMATION)) {
			addPositionComponent(entity);
			addSpriteComponent(entity);
		}
	}

	private void addPositionComponent(IEntity entity) {
		entity.setSpec(Position.class, 1);
		Position pos = new Position();
		entity.addComponent(pos);
	}

	private void addSpriteComponent(IEntity entity) {
		File file = FileUtilities.promptAndGetFile(FileUtilities.getImageFilters(), 
				myResources.getString("pickImagePathImage"), DefaultStrings.GUI_IMAGES.getDefault());
		entity.setSpec(Sprite.class, 1);
		entity.addComponent(new Sprite(file.getPath()));
	}

	public ILevel getLevel() {
		return view.getLevel();
	}

	@Override
	public ScrollPane getPane() {
		return scrollPane;
	}

	public boolean displayContains(IEntity checkEntity) {
		return masterEntityList.contains(checkEntity);
	}

	public boolean environmentContains(IEntity checkEntity) {
		return view.getEntitySystem().containsEntity(checkEntity);
	}

}
