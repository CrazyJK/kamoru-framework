package jk.kamoru.util;

/**
 * commons.lang3.ArrayUtils 상속받아 필요한 기능 추가
 * @author kamoru
 *
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {

	/**
	 * 배열속에 {@code null}아닌 요소의 수를 반환
	 * @param array 요소 개수를 구할 배열, may be null
	 * @return length 실제 요소 개수. 배열이 {@code null}이면 0
	 */
	public static int getRealLength(Object[] array) {
		if(array == null) {
			return 0;
		}
		int length = 0;
		for(int i=0; i<array.length; i++) {
			if(array[i] != null) {
				length++;
			}
		}
		return length;
	}
	
	/**배열의 요소를 ,(comma)로 구분한 문자열 반환
	 * @param array the array to get a toString for, may be null
	 * @return 배열의 문자열 표현
	 */
	public static String toStringComma(Object[] array) {
		return StringUtils.replaceEach(toString(array), new String[] {"{", "}"}, new String[] {"", ""});
	}

}
