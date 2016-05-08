package view.editor.environmenteditor;

import java.io.File;
import java.util.ResourceBundle;

import api.IEntity;
import javafx.scene.control.Alert.AlertType;
import model.component.movement.Position;
import model.component.visual.Sprite;
import view.enums.DefaultStrings;
import view.utilities.Alerts;
import view.utilities.FileUtilities;

public class ComponentAdderUtility {
	
	public static void addViewComponents(IEntity entity, ResourceBundle bundle) {
		if (Alerts.showAlert(bundle.getString("confirm"), bundle.getString("componentsRequired"),
				bundle.getString("addComponentQuestion"), AlertType.CONFIRMATION)) {
			addPositionComponent(entity);
			addSpriteComponent(entity, bundle);
		}
	}

	private static void addPositionComponent(IEntity entity) {
		entity.setSpec(Position.class, 1);
		Position pos = new Position();
		entity.addComponent(pos);
	}

	private static void addSpriteComponent(IEntity entity, ResourceBundle bundle) {
		File file = FileUtilities.promptAndGetFile(FileUtilities.getImageFilters(),
				bundle.getString("pickImagePathImage"), DefaultStrings.GUI_IMAGES.getDefault());
		entity.setSpec(Sprite.class, 1);
		entity.addComponent(new Sprite(file.getPath()));
	}

}
