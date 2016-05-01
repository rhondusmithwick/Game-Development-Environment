package testing;

import api.IEntity;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import datamanagement.XMLReader;
import model.component.movement.Position;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Created by rhondusmithwick on 4/8/16.
 *
 * @author Rhondu Smithwick
 */
public class MoveEntityTest {
    private static final String LOAD_FILE_NAME = "resources/savedEntities/player.xml";
    private static final String GROOVY_SCRIPT = "resources/groovyScripts/TeleportEntitytity.groovy";
    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");

    private final IEntity entity = new XMLReader<IEntity>().readSingleFromFile(LOAD_FILE_NAME);

    @Test
    public void moveTest () {
        double oldX = entity.getComponent(Position.class).getX();
        engine.put("entity", entity);
        double moved = 100;
        try {
            String input = Files.toString(new File(GROOVY_SCRIPT), Charsets.UTF_8);
            input = input.replace("$1", String.valueOf(moved)); // TODO use binding instead!!
            engine.eval(input);
        } catch (ScriptException | IOException e) {
            e.printStackTrace();
        }
        assertEquals(oldX + 100, ((Position) engine.get("position")).getX(), .001);
    }

}
