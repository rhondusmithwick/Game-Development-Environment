package model.component.audio;

import java.io.File;

import api.IComponent;
import javafx.scene.media.AudioClip;

public class SoundEffect implements IComponent {
	private final AudioClip audioClip;
	
	public SoundEffect(String path) {
		audioClip = new AudioClip(new File(path).toURI().toString());
		
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
}
