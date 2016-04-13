package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.IEntitySystem;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import events.Action;
import model.entity.EntitySystem;

/**
 * Created by rhondusmithwick on 4/12/16.
 *
 * @author Rhondu Smithwick
 */
public class UniverseTest {
    IEntitySystem universe = new EntitySystem();

    public static void main(String[] args) {
        new UniverseTest().test();
    }

    public void test() {
    	Map<String, Object> map = new HashMap<>();
    	map.put("HEYHEY","YOUYOU");
    	List<Action> actionList = new ArrayList<>();
        Action action = new Action("BLAH", map);
        Action action3 = new Action("asdfadf", map);
        actionList.add(action);
        actionList.add(action3);
        new XMLWriter<Action>().writeToFile("heeey.xml", actionList);
        List<Action> actionList2 = new XMLReader<Action>().readFromFile("heeey.xml");
    }

}
