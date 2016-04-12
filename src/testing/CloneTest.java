package testing;

import datamanagement.XMLReader;
import datamanagement.XMLWriter;
import model.component.movement.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by rhondusmithwick on 4/12/16.
 *
 * @author Rhondu Smithwick
 */
public class CloneTest {

    @Test
    public void testClone() {
        Position position = new Position(30, 40);
        String clonedString = new XMLWriter<Position>().writeToString(position);
        Position position2 = new XMLReader<Position>().readSingleFromString(clonedString);
        position2.setX(80);
        assertEquals(position.getX(), 30, .001);
        assertEquals(position2.getX(), 80, .001);
    }
}
