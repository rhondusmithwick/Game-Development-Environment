package model.component.audio;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.AudioClip;
import utility.SingleProperty;

@SuppressWarnings("serial")
public class SoundEffect implements IComponent {
	private static final String DEFAULT = "resources/soundfx/laser.mp3";
	private final SingleProperty<String> effect = new SingleProperty<>("SoundEffect", DEFAULT);
	private transient AudioClip audioClip;
	
	public SoundEffect(String path) {
		setSound(path);
	}
	
	public SoundEffect(){
		setSound(DEFAULT);
	}
	
	public void play() {
		audioClip.play();
	}
	
	public void stop() {
		audioClip.stop();
	}
	
	public void loop() {
		audioClip.setCycleCount(AudioClip.INDEFINITE);
		play();
	}
	
	public void setSound(String path){
		effect.property1().set(path);
		audioClip = new AudioClip(new File(path).toURI().toString());
	}
	
	public boolean isPlaying() {
		return audioClip.isPlaying();
	}
	
	public String getSound(){
		return effect.property1().get();
	}
	
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.audioClip = new AudioClip(new File(effect.property1().get()).toURI().toString());
    }
    
    @Override
    public List<SimpleObjectProperty<?>> getProperties() {
        return Arrays.asList(effect.property1());
    }
    
	@Override
	public void update() {
		setSound(getSound());
	}
}
