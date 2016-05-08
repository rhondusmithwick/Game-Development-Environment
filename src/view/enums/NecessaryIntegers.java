package view.enums;

public enum NecessaryIntegers {

	MILLISECOND_DELAY(10), SECOND_DELAY(MILLISECOND_DELAY.getNumber() / 1000);

	private final double number;

	/**
	 * creates new number enum for component name
	 *
	 * @param number
	 *            number for component
	 */
	NecessaryIntegers(double number) {
		this.number = number;
	}

	/**
	 * returns int number for given enum name
	 *
	 * @return int number
	 */
	public double getNumber() {
		return number;
	}
}
