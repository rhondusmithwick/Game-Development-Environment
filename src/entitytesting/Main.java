package entitytesting;

import java.io.Serializable;
import java.util.List;

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
        Entity entity1 = entitySystem.createEntity("player");
        Position position1 = entity1.getComponent(Position.class);
        System.out.println("Created: " + position1);

        Entity entity2 = entitySystem.createEntity("player");
        Position position2 = entity2.getComponent(Position.class);
        writeToXML(position1, position2);
        readFromXML();
    }

    private void writeToXML(Serializable... ser) {
        XMLWriter writer = new XMLWriter(SERIALIZED_FILE_NAME);
        writer.writeAll(ser);
    }

    private void readFromXML() {
        XMLReader reader = new XMLReader(SERIALIZED_FILE_NAME);
        List<Component> components = reader.readAll(Component.class);
        System.out.println(String.format("Read from %s: %s", SERIALIZED_FILE_NAME, components));
    }
}
