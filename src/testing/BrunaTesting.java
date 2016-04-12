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
<<<<<<< HEAD
    public void setUp () {
	myGameEditor = new GameEditor(null, "propertyFiles/english");
}
=======
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
>>>>>>> 7afd78a5dd5b70eac2f662f2e84dd5a29d4df68a

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
