package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import enums.GUISize;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.entity.Entity;
import model.entity.EntitySystem;
import view.editor.EditorEnvironment;


public class BrunaTesting {
	
	 private EditorEnvironment myEditorEnvironment;
	private Stage myStage;
//	private Scene myScene;
//	private static final String LANGUAGE = "English";

	@Before
	    public void setUp () {
		System.out.println("ro aww xon");
		myStage = new Stage();
		myStage.setTitle("Testing");
		myStage.setWidth(GUISize.MAIN_SIZE.getSize());
		myStage.setHeight(GUISize.MAIN_SIZE.getSize());
		//myEditorEnvironment = new EditorEnvironment(new EntitySystem(), "English", new Button());
		//myScene = new Scene(myEditorEnvironment.getPane(), GUISize.MAIN_SIZE.getSize(), GUISize.MAIN_SIZE.getSize());
		
		myStage.show();
	}

	@Test
	public void testAcceptsEntitySystem() {
		EntitySystem entitySystem = new EntitySystem();
		myEditorEnvironment.addEntitySystem(entitySystem);
		assertNotNull(myEditorEnvironment.getEntitySystem());
	}
	
	@Test 
	public void testContainsEntities() {
		EntitySystem entitySystem = new EntitySystem();
		entitySystem.addEntities(new Entity());
		myEditorEnvironment.addEntitySystem(entitySystem);
		assertNotNull(myEditorEnvironment.getEntity(null));
	}

}
