package usecases;

import entitytesting.Entity;
import entitytesting.EntitySystem;
import entitytesting.Position;
import entitytesting.SerializableReader;

/**
 * Created by rhondusmithwick on 3/31/16.
 * <p>
 * A game creator adds either a built or preloaded character to a level
 *
 * @author Rhondu Smithwick
 */
public class UseCase6 {

    private final EntitySystem entitySystem;

    public UseCase6(EntitySystem entitySystem) {
        this.entitySystem = entitySystem;
    }

    // get a preloaded player entity from a file
    void doUseCasePreloaded() {
        Entity entity = new SerializableReader<Entity>("player.ser").readSingle();
        entitySystem.addEntity(entity);
    }

    // create own player entity
    void doUseCaseBuild() {
        Entity entity = entitySystem.createEntity("");
        entity.addComponent(new Position(500, 500));
        // other add compeontns
    }
}
