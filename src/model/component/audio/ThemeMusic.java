package model.component.audio;

import api.IComponent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import utility.SingleProperty;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("serial")
public class ThemeMusic implements IComponent {
    private static final String DEFAULT = "resources/music/finalCountdown.mp3";
    private final SingleProperty<String> music = new SingleProperty<>("ThemeMusic", DEFAULT);
    private transient MediaPlayer mediaPlayer;

    public ThemeMusic (String path) {
        setSound(path);
    }

    public ThemeMusic () {
        this(DEFAULT);
    }

    public void play () {
        mediaPlayer.play();
    }

    public void pause () {
        mediaPlayer.pause();
    }

    public void stop () {
        mediaPlayer.stop();
    }

    public void loop () {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        play();
    }

    public boolean isPlaying () {
        return mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public String getSound () {
        return music.property1().get();
    }

    public void setSound (String path) {
        music.property1().set(path);
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

    private void writeObject (ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setSound(music.property1().get());
    }

    @Override
    public List<SimpleObjectProperty<?>> getProperties () {
        return Collections.singletonList(music.property1());
    }

    @Override
    public void update () {
        setSound(getSound());
    }
}
