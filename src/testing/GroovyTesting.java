package testing;

import model.component.movement.Position;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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
        try {
            engine.put("position1", position1);
            engine.eval("import model.component.movement.Position; position = new Position(50, 50)");
            engine.eval("position.setX(100)");
            engine.eval("position.setY(5000)");
            engine.eval("position1.setX(888888)");
            Position position = (Position) engine.get("position");
            System.out.println("Created by groovy position" + position);
            System.out.println("Created by Java Position: " + position1);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
