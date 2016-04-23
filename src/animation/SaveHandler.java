package animation;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Created by rhondusmithwick on 4/20/16.
 *
 * @author Rhondu Smithwick
 */
class SaveHandler {

    private SaveHandler() {
    }

    public static void saveAnimations(Map<String, Map<String, String>> maps) {
        Properties properties = new Properties();
        for (String animationName : maps.keySet()) {
            Map<String, String> props = maps.get(animationName);
            for (Entry<String, String> prop : props.entrySet()) {
                String key = animationName + prop.getKey();
                properties.put(key, prop.getValue());
            }
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Animation To File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter ("PROPERTIES Dateien (*.properties)", "*.properties");
        fileChooser.getExtensionFilters().add (extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        try {
            properties.store(new FileWriter(file), "MELISSA IS MAKING ME USE THIS WEIRD ASS MAP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveImage(Image image) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                BufferedImage imageToWrite = SwingFXUtils.fromFXImage(image,
                        null);
                ImageIO.write(imageToWrite, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
