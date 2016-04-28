package voogasalad.util.explorer;



import javafx.scene.input.DataFormat;

/**
 * A custom DataFormat to allow for D&D functionality.
 * @author DoovalSalad
 *
 */
public class VoogaFileFormat extends DataFormat {
	
	  private VoogaFileFormat(String format) {
		  super(format);
	  }
	 
	  private static class SingletonHolder { 
	    private static final VoogaFileFormat INSTANCE = new VoogaFileFormat("authoring.resourceutility.VoogaFile");
	  }

	  public static VoogaFileFormat getInstance() {
	    return SingletonHolder.INSTANCE;
	  }

}
