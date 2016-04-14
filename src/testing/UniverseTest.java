package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.IEntity;
import api.IEntitySystem;
import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import events.Action;
import events.EventSystem;
import events.Trigger;
import model.component.character.Health;
import model.entity.Entity;
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
    	
    	EventSystem eventSystem = new EventSystem(universe);
    	Map<String, Object> map = new HashMap<>();
    	map.put("HEYHEY","YOUYOU");
    	List<Action> actionList = new ArrayList<>();
        Action action = new Action("BLAH", map);
        Action action3 = new Action("asdfadf", map);
        actionList.add(action);
        actionList.add(action3);
        new XMLWriter<Action>().writeToFile("heeey.xml", actionList);
        List<Action> actionList2 = new XMLReader<Action>().readFromFile("heeey.xml");
        IEntity character = new Entity("Ani");
        character.addComponent(new Health(100.0));
        character.getComponent(Health.class).getProperties().get(0);
        
        /*universe.addEntity(character);
        Trigger t = new Trigger(character.getID(), character.getComponent(Health.class), 0, universe);
        t.serialize("yoyoyo.xml");
        Trigger t2 = new XMLReader<Trigger>().readSingleFromFile("yoyoyo.xml");
        eventSystem.registerEvent(t, action);
        eventSystem.saveEventsToFile("eventtest.xml");
        EventSystem eventSystem2 = new EventSystem(universe);
        eventSystem2.readEventsFromFile("eventtest.xml");*/
    }

}
