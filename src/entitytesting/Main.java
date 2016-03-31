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
        entitySystem.addComponent(Position.class,entity);
        Position position = entitySystem.getComponent(entity, Position.class);
        System.out.println(position.getX());
        position.setX(50);
        System.out.println(position.getX());
    }
}
