package usecases;

import model.component.movement.Position;
import model.entity.Entity;
import model.entity.EntitySystem;
import serialization.SerializableReader;

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
        Entity entity = entitySystem.createEntityFromDefault("");
        entity.addComponent(new Position(500.0, 500.0));
        // other add compeontns
    }
}
