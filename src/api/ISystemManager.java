package api;

import java.util.List;

import datamanagement.XMLReader;

/**
 * Created by rhondusmithwick on 3/31/16.
 *
 * @author Rhondu Smithwick
 */
public interface ISystemManager extends ISerializable {

	/**
	 * This will pause the game loop.
	 */
	void pauseLoop();

	/**
	 * This will step the game's loop.
	 */
	void step(double dt);

	/**
	 * Get the current entity system
	 *
	 * @return IEntitySystem-type entity system
	 */
	IEntitySystem getEntitySystem();

	/**
	 * Get the current event system
	 *
	 * @return IEventSystem-type event system
	 */
	IEventSystem getEventSystem();

	/**
	 * Reads objects from file with specified fileName. Must be of type T.
	 *
	 * @param fileName
	 *            the file
	 * @param <T>
	 *            the type of all the objects.
	 * @return list of objects of type T
	 */
	static <T extends ISerializable> List<T> evaluate(String fileName) {
		return new XMLReader<T>().readFromFile(fileName);
	}

	/**
	 * Reads objects from specified string. Must be of type T.
	 *
	 * @param stringToReadFrom
	 *            the string
	 * @param <T>
	 *            the type of all the objects.
	 * @return list of objects of type T
	 */
	static <T extends ISerializable> List<T> evaluateString(String stringToReadFrom) {
		return new XMLReader<T>().readFromString(stringToReadFrom);
	}
}
