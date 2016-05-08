// This entire file is a subpart of my masterpiece just to show the full MVC triad.

package view.editor.environmenteditor;

import api.IEntity;
import api.IEntitySystem;
import api.ILevel;
import groovy.lang.GroovyShell;
import javafx.scene.Scene;
import model.core.SystemManager;
import update.GameLoopManager;

public class EnvironmentEditorModel {

	private SystemManager systemManager;
	private GameLoopManager gameManager;

	EnvironmentEditorModel(String language, Scene scene, ILevel level) {
		systemManager = new SystemManager(scene, level);
		gameManager = new GameLoopManager(language, systemManager);
		setScene(scene);
	}

	public String evaluate(String text) {
		String command = text.substring(text.lastIndexOf("\n")).trim();
		String stringToPrint = null;
		try {
			Object result = systemManager.getShell().evaluate(command);
			if (result != null) {
				stringToPrint = result.toString();
			}
		} catch (Exception e) {
			stringToPrint = e.getMessage();
		}
		return stringToPrint;
	}

	public void step(double dt) {
		systemManager.step(dt);
	}

	public void createLoopManager() {
		gameManager.show();
	}

	public void play() {
		systemManager.play();
	}

	public void pauseLoop() {
		systemManager.pauseLoop();
	}

	public IEntitySystem getEntitySystem() {
		return systemManager.getEntitySystem();
	}

	public ILevel getLevel() {
		return systemManager.getLevel();
	}

	public void setScene(Scene scene) {
		systemManager.getLevel().setOnInput(scene);
	}

	public void addEntity(IEntity copyEntity) {
		systemManager.getLevel().addEntity(copyEntity);
	}

	public GroovyShell getShell() {
		return systemManager.getShell();
	}

}
