package testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.editor.EditorEnvironment;


public class BrunaTesting {
	
	 private EditorEnvironment myEditorEnvironment;

	@Before
	    public void setUp () {
	        myEditorEnvironment = new EditorEnvironment();
	    }

	@Test
	public void testAcceptsEntitySystem() {
		myEditorEnvironment.addEntitySystem();
	}

}
