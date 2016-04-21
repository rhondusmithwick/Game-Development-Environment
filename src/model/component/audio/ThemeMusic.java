package model.component.audio;

import java.io.File;

import api.IComponent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class ThemeMusic implements IComponent {
	private final Media media;
	private final MediaPlayer mediaPlayer;
	
	public ThemeMusic(String path) {
		media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
	}
	
	public void play() {
		mediaPlayer.play();
	}
	
	public void pause() {
		mediaPlayer.pause();
	}
	
	public void stop() {
		mediaPlayer.stop();
	}
	
	public void loop() {
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		play();
	}
	
	public boolean isPlaying() {
		return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}
}
