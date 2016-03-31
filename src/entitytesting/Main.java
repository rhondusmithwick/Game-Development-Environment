package entitytesting;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Main {

    private static final String SERIALIZED_FILE_NAME = "entityTest.xml";

    public static void main(String[] args) {
        new Main().test();
    }

    public void test() {
        EntitySystem entitySystem = new EntitySystem();
        entitySystem.createEntity("player");
        Entity entity = entitySystem.getEntity(1);
        Position position = entity.getComponent(Position.class);
        System.out.println("Created: " + position);
        writeToXML(position);
        readFromXML();
    }

    private void writeToXML(Serializable ser) {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("ERROR: While Creating or Opening the File " + SERIALIZED_FILE_NAME);
        }
        encoder.writeObject(ser);
        encoder.close();
    }

    private void readFromXML() {
        XMLDecoder decoder=null;
        try {
            decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: While Opening the File " + SERIALIZED_FILE_NAME);
        }
        Object obj =  decoder.readObject();
        System.out.println(String.format("Read from %s: %s", SERIALIZED_FILE_NAME, obj));
    }
}
