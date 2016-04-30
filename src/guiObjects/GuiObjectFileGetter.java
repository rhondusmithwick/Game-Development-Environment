package guiObjects;

import java.io.File;

import javafx.beans.property.SimpleObjectProperty;
import utility.FilePathRelativizer;

public abstract class GuiObjectFileGetter extends GuiObject{
	private SimpleObjectProperty<String> property;
	public GuiObjectFileGetter(String name, String resourceBundle, SimpleObjectProperty<String> property) {
		super(name, resourceBundle);
		property = this.property;
	}

	
	protected void setFile(File file){
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
