//package testing;
//
//import api.IEntity;
//import com.google.common.base.Charsets;
//import com.google.common.io.Files;
//import events.Action;
//import events.UniverseAction;
//import model.component.movement.Position;
//import model.entity.Entity;
//import model.entity.Level;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Created by rhondusmithwick on 4/9/16.
// *
// * @author Rhondu Smithwick
// */
//public class ActionTest {
//    private final Level universe = new Level();
//
//    private final String MOVE_SCRIPT = "resources/groovyScripts/SignalScript.groovy";
//
//    @Test
//    public void signalTest() {
//        IEntity entity = new Entity("Ben");
//        Position position = new Position();
//        entity.forceAddComponent(position, true);
//        universe.addEntity(entity);
//        try {
//            String script = Files.toString(new File(MOVE_SCRIPT), Charsets.UTF_8);
//            Action action = new UniverseAction(script, universe);
//            action.activate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assertEquals(position.getX(), 50, .001);
//    }
//
//}
