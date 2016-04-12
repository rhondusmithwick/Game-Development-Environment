package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import api.IEntity;
import model.entity.Entity;
import view.editor.EditorEnvironment;
import view.editor.gameeditor.GameEditor;


public class BrunaTesting {
	
	 private EditorEnvironment myEditorEnvironment;
	private GameEditor myGameEditor;
	
	
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
	public void listConnectionTest(){
	//myEditorEnvironment = new EditorEnvironment("propertyFiles/english", null, myGameEditor.getMasterList(),
	//myGameEditor.getAddToList());
	//myEditorEnvironment.populateLayout();
	IEntity checkEntity = new Entity("Check For");
	//myGameEditor.addToMaster(checkEntity);
	assertTrue(myEditorEnvironment.displayContains(checkEntity));
	
}

}
