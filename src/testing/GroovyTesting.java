package testing;

import model.component.movement.Position;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by rhondusmithwick on 4/7/16.
 *
 * @author Rhondu Smithwick
 */
public class GroovyTesting implements Tester {
    private final ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");


    public static void main(String[] args) {
        new GroovyTesting().test();
    }

    @Override
    public void test() {
        Position position1 = new Position(80, 500);
        engine.put("position1", position1);
        new Thread(this::run).start();
        Position position = (Position) engine.get("position");
        System.out.println("Created by groovy position" + position);
        System.out.println("Created by Java Position: " + position1);
    }

    private void run() {
        try {
            engine.eval(new FileReader("resources/groovyScripts/PositionTest.groovy"));
        } catch (ScriptException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
