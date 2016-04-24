package animation;

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

import static javafx.embed.swing.SwingFXUtils.fromFXImage;

/**
 * Created by rhondusmithwick on 4/20/16.
 *
 * @author Rhondu Smithwick
 */
class SaveHandler {

    private SaveHandler() {
    }

    public static void saveAnimations(String spriteSheetPath, Map<String, Map<String, String>> maps) {
        Properties properties = new Properties();
        properties.put("FilePath", spriteSheetPath);
        properties.putAll(createProperties(maps));
        FileChooser fileChooser = createFileChooser("Save Animation to File", new FileChooser.ExtensionFilter("PROPERTIES Dateien (*.properties)", "*.properties"));
        File file = fileChooser.showSaveDialog(new Stage());
        try {
            properties.store(new FileWriter(file), "MELISSA IS MAKING ME USE THIS WEIRD ASS MAP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Properties createProperties(Map<String, Map<String, String>> maps) {
        Properties properties = new Properties();
        for (String animationName : maps.keySet()) {
            Map<String, String> props = maps.get(animationName);
            for (Entry<String, String> prop : props.entrySet()) {
                String key = animationName + prop.getKey();
                properties.put(key, prop.getValue());
            }
        }
        return properties;
    }

    public static void saveImage(Image image) {
        FileChooser fileChooser = createFileChooser("Save Image", new FileChooser.ExtensionFilter("Image Files", "png"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                BufferedImage imageToWrite = fromFXImage(image,
                        null);
                ImageIO.write(imageToWrite, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static FileChooser createFileChooser(String title, FileChooser.ExtensionFilter filter) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(filter);
        return fileChooser;
    }
}
