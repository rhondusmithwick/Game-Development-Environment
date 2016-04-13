package testing;

import api.IEntitySystem;
import datamanagement.XMLReader;
import events.UniverseAction;
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
        UniverseAction action = new UniverseAction("", universe);
        action.serialize("heeey.xml");
        UniverseAction action2 = new XMLReader<UniverseAction>().readSingleFromFile("heeey.xml");
        System.out.println(action.getUniverse() == action2.getUniverse());
    }

}
