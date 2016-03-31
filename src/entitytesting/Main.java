package entitytesting;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by rhondusmithwick on 3/30/16.
 *
 * @author Rhondu Smithwick
 */
public class Main {

    public static void main(String[] args) {
        EntitySystem entitySystem = new EntitySystem();
        entitySystem.createEntity();
        Entity entity = entitySystem.getEntity(1);
        entity.addComponent(new Position(50, 30), entity);
        Position position = entity.getComponent(Position.class);
        System.out.println(position);
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("entity.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(entity);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
        try {
            FileInputStream fileIn = new FileInputStream("entity.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Entity e = (Entity) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Read in info");
            System.out.println(e.getComponent(Position.class));
        } catch(IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }
}
