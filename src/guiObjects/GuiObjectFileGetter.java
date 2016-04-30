package guiObjects;

import java.io.File;

import javafx.beans.property.SimpleObjectProperty;
import utility.FilePathRelativizer;

public abstract class GuiObjectFileGetter extends GuiObject{
	public GuiObjectFileGetter(String name, String resourceBundle) {
		super(name, resourceBundle);
	}

	
	protected void setFile(File file, SimpleObjectProperty<String> property){
		if(file==null){
			return;
		}
		property.set(FilePathRelativizer.relativize(file.getPath()));
		setPreview(file);
		
	}
	
	protected abstract void setPreview(File file);


	@Override
	public abstract Object getCurrentValue();

	@Override
	public abstract Object getGuiNode();

}
