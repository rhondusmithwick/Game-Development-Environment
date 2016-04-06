package api;

import java.io.File;
import java.io.Serializable;


public interface ISerializable extends Serializable {
	/**
	 * Evaluates the File f
	 * @param f File to evaluate
	 */
    default void evaluate(File f) {
        // TODO
		System.out.println("ISerializable evaluate() not implemented");
		System.exit(1);
    }
    
    /**
	 * Evaluates the String s
	 * @param s String to evaluate
	 */
    default void evaluateString(String s) { // TODO
		System.out.println("ISerializable evaluateString() not implemented");
		System.exit(1);
    }

    /**
	 * Serializes this object to a File
	 * @return File representing the serialized form of this object
	 */
    default File serialize() {
		System.out.println("ISerializable serialize() not implemented");
		System.exit(1);
        return null; // TODO
    }

    /**
	 * Serializes this object to a String
	 * @return String representing the serialized form of this object
	 */
    default String serializeToString() { // TODO
		System.out.println("ISerializable serializeToString() not implemented");
		System.exit(1);
		return null;
    }
}
