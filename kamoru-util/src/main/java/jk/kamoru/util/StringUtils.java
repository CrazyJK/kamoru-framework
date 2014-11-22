package jk.kamoru.util;



/**
 * commons.lang3.StringUtils 상속하고 필요한 기능 추가
 * @author kamoru
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/**returns {@code true} if {@code null} or equalsIgnoreCase("null") or length == 0
	 * @param str 검사할 문자열
	 * @return 검사 결과
	 */
	public static boolean isNullOrBlank(String str) {
		return str == null || str.equalsIgnoreCase("null") || str.length() == 0;
	}
	
	/**{@code number}를 자리수 {@code digit}만큼 0을 붙인 문자열 반환
	 * @param number 0을 붙일 숫자
	 * @param digit 자리수
	 * @return 0붙인 문자열
	 */
	public static String addZero(int number, int digit) {
		String s = String.valueOf(number);
		while(s.length() < digit) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 문자열 비교<br> 
	 * null일 경우 ""으로 변환. trim처리 후<br>
	 * {@link String#compareTo(String)} 사용
	 * @param text1
	 * @param text2
	 * @return 비교 결과 
	 */
	public static int compareTo(String text1, String text2) {
		text1 = text1 == null ? "" : text1.trim();
		text2 = text2 == null ? "" : text2.trim();
		return text1.compareTo(text2);
	}

	/**객체 비교<br>
	 * {@link Object#toString()}으로 변환 후<br>
	 * {@link String#compareTo(String)} 사용
	 * 객체가 null이면 ""으로 처리 
	 * @param obj1
	 * @param obj2
	 * @return 비교 결과
	 */
	public static int compareTo(Object obj1, Object obj2) {
		String str1 = obj1 == null ? "" : obj1.toString();
		String str2 = obj2 == null ? "" : obj2.toString();
		return str1.compareTo(str2);
	}

	/**
	 * 문자열 비교. null일 경우 ""으로 변환. trim, IgnoreCase
	 * @param name1
	 * @param name2
	 * @return 비교 결과
	 */
	public static int compareToIgnoreCase(String name1, String name2) {
		name1 = name1 == null ? "" : name1.trim().toLowerCase();
		name2 = name2 == null ? "" : name2.trim().toLowerCase();
		return name1.compareTo(name2);
	}

	public static String trimToEmpty(Object object) {
		return object == null ? "" : trimToEmpty(object.toString());
	}

}
