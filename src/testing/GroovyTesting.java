package testing;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import model.component.movement.Position;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;

/**
 * Created by rhondusmithwick on 4/7/16.
 *
 * @author Rhondu Smithwick
 */
public class GroovyTesting implements Tester {

    private static final String GROOVY_SCRIPT = "resources/groovyScripts/PositionTest.groovy";
    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");

    public static void main(String[] args) {
        new GroovyTesting().test();
    }

    @Override
    public void test() {
        Position position1 = new Position(80, 500);
        engine.put("position1", position1);
        run();
        Position position = (Position) engine.get("position");
        System.out.println("Created by groovy position" + position);
        System.out.println("Created by Java Position: " + position1);
    }

    private void run() {
        try {
            String input = Files.toString(new File(GROOVY_SCRIPT), Charsets.UTF_8);
            input = input.replace("$1", "position1"); // TODO use binding instead!!
            engine.eval(input);
        } catch (ScriptException | IOException e) {
            e.printStackTrace();
        }
    }
}
