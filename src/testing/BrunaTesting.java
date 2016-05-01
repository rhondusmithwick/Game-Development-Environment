package testing;

import api.IEntity;
import model.entity.Entity;
import org.junit.Before;
import org.junit.Test;
import view.editor.environmenteditor.EditorEnvironment;
import view.editor.gameeditor.GameEditor;

import static org.junit.Assert.assertTrue;


public class BrunaTesting {

	private EditorEnvironment myEditorEnvironment;
	private GameEditor myGameEditor;


	@Before

	public void setUp () {
//		myGameEditor = new GameEditor(null, "propertyFiles/english");
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
