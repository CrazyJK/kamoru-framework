package jk.kamoru.util;


public class Assert {

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new UtilException(message);
		}
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

}
