package entitytesting;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Main {
    private static final String SERIALIZED_FILE_NAME = "resources/player.ser";

    public static void main (String[] args) {
        new Main().test();
    }

    public void test () {
        EntitySystem entitySystem = new EntitySystem();
        Entity entity = entitySystem.createEntity("player");
        Entity entity2 = entitySystem.createEntity("player");
        // writeToXML(entity);
        // readFromXML();
        writeToSeralize(entity, entity2);
        readFromSerialize();
    }

    private void writeToXML (Serializable ... ser) {
        XMLWriter writer = new XMLWriter(SERIALIZED_FILE_NAME);
        writer.writeAll(ser);
    }

    private void writeToSeralize (Serializable ... sers) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(SERIALIZED_FILE_NAME);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Serializable ser : sers) {
                out.writeObject(ser);
            }
            out.close();
            fileOut.close();
        }
        catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void readFromXML () {
        XMLReader reader = new XMLReader(SERIALIZED_FILE_NAME);
        List<Entity> entities = reader.readAll(Entity.class);
        System.out.println(String.format("Read from %s: %s", SERIALIZED_FILE_NAME, entities));

    }

    private void readFromSerialize () {
        List<Entity> entities = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream(SERIALIZED_FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            while (true) {
                try {
                    Entity entity = (Entity) in.readObject();
                    entities.add(entity);
                }
                catch (IOException e) {
                    break;
                }
            }
            in.close();
            fileIn.close();
        }
        catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        System.out.println(entities);
    }
}
