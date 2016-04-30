package guiObjects;


import java.io.File;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import utility.FilePathRelativizer;
import view.enums.DefaultStrings;
import view.enums.FileExtensions;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

public class GuiObjectMusicChooser extends GuiObject{
	private Button setMusic, play;
	private ResourceBundle myResources, myPropertiesNames;
	private SimpleObjectProperty<String> property;
	private AudioClip preview;
	
	@SuppressWarnings("unchecked")
	public GuiObjectMusicChooser(String name, String resourceBundle, String language, SimpleObjectProperty<?> property, Object object) {
		super(name, resourceBundle);
		this.myPropertiesNames = ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
		myResources = ResourceBundle.getBundle(language);
		setMusic = ButtonFactory.makeButton(myPropertiesNames.getString(name), e -> changeMusic());
		play = ButtonFactory.makeButton(myResources.getString("play"), e -> playMusic());
		this.property = (SimpleObjectProperty<String>) property;
		setPreview(new File(this.property.getValue()));
	}
	
	private void playMusic() {
		preview.setCycleCount(1);
		stopMusic();
		preview.play();
	}

	private void changeMusic(){
		File file = getMusic();
		setPreview(file);
	}

	private File getMusic() {
		return FileUtilities.promptAndGetFile(FileExtensions.MP3.getFilter(),
				myResources.getString("ChooseFile"), DefaultStrings.RESOURCES.getDefault());		
	}

	@Override
	public Object getCurrentValue() {
		return null;
	}
	
	private void setPreview(File file) {
		if(file==null){
			return;
		}
		property.setValue(FilePathRelativizer.relativize(file.getPath()));
		stopMusic();
		preview = new AudioClip(file.toURI().toString());
	}

	@Override
	public Object getGuiNode() {
		HBox h = new HBox(GUISize.GUI_IM_DISP.getSize());
		h.getChildren().addAll(setMusic, play);
		return h;
	}
	
	private void stopMusic() {
		if(preview != null && preview.isPlaying()) {
			preview.stop();
		}
	}
}
