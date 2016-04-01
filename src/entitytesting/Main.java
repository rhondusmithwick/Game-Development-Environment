package entitytesting;

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
        new SerializableWriter(SERIALIZED_FILE_NAME).write(entity);
        List<Entity> entities = new SerializableReader<Entity>(SERIALIZED_FILE_NAME).read();
        System.out.println("Read: " + entities);
    }

}
