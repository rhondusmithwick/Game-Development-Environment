package animation;

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
public class SaveHandler {

    private SaveHandler() {
    }

    public static void save(String fileName, Map<String, Map<String, String>> maps) {
        Properties properties = new Properties();
        for (String animationName : maps.keySet()) {
            Map<String, String> props = maps.get(animationName);
            for (Entry<String, String> prop : props.entrySet()) {
                String key = animationName + prop.getKey();
                System.out.println(key);
                properties.put(key, prop.getValue());
            }
        }
        try {
            properties.store(new FileWriter(new File(fileName)), "MELISSA IS MAKING ME USE THIS WEIRD ASS MAP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
