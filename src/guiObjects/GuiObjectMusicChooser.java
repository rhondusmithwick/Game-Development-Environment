package guiObjects;


import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import view.enums.DefaultStrings;
import view.enums.GUISize;
import view.utilities.ButtonFactory;
import view.utilities.FileUtilities;

import java.io.File;
import java.util.ResourceBundle;

/**
 * @author calinelson, Ben Zhang
 */
public class GuiObjectMusicChooser extends GuiObjectFileGetter {
    private Button setMusic, play, stop;
    private ResourceBundle myResources, myPropertiesNames;
    private SimpleObjectProperty<String> property;
    private AudioClip preview;
    private TextField text = new TextField();

    @SuppressWarnings("unchecked")
    public GuiObjectMusicChooser (String name, String resourceBundle, String language, SimpleObjectProperty<?> property) {
        super(name, resourceBundle);
        this.myPropertiesNames = ResourceBundle.getBundle(language + DefaultStrings.PROPERTIES.getDefault());
        myResources = ResourceBundle.getBundle(language);
        text.setEditable(false);
        play = ButtonFactory.makeButton(myResources.getString("play"), e -> playMusic());
        stop = ButtonFactory.makeButton(myResources.getString("stop"), e -> stopMusic());
        this.property = (SimpleObjectProperty<String>) property;
        setMusic = ButtonFactory.makeButton(myPropertiesNames.getString(name), e -> changeValue(this.property, myResources, DefaultStrings.SOUNDFX.getDefault(), FileUtilities.getMusicFilters()));
        setFile(new File(this.property.getValue()), this.property);
    }

    private void playMusic () {
        stopMusic();
        preview.setCycleCount(1);
        preview.play();
    }

    private void stopMusic () {
        if (preview != null && preview.isPlaying()) {
            preview.stop();
        }
    }


    @Override
    public Object getCurrentValue () {
        return null;
    }

    @Override
    protected void setPreview (File file) {
        text.setText(file.getName());
        stopMusic();
        preview = new AudioClip(file.toURI().toString());
    }

    @Override
    public Object getGuiNode () {
        int GUI_IM_DISP = GUISize.GUI_IM_DISP.getSize();
        HBox h = new HBox(GUI_IM_DISP);
        VBox left = new VBox(GUI_IM_DISP);
        VBox right = new VBox(GUI_IM_DISP);
        left.getChildren().addAll(setMusic, text);
        right.getChildren().addAll(play, stop);
        h.getChildren().addAll(left, right);
        return h;
    }


}
