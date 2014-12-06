package net.wecash.common.util;

/**
 * 
 * @author xkk
 *
 */
public class StringUtils {
	public static final String CANDIDATE_INTEGER = "1234567890";
	public static final String CANDIDATE_CHARS = "3456789ABCDEFGHJKMNPQRSTUVWXY";
	public StringUtils() {

	}

	public static boolean isEmpty(String s) {
		if(s == null || "".equals(s.trim())) {
			return true;
		}
		return false;
	}
	
	public static String ltrim(String s) {
		if(isEmpty(s)) return "";
		
		int length = s.length();
		int i = 0;
		char[] chars = new char[length];
		s.getChars(0, length - 1, chars, 0);
		
		while(i <= length && chars[i] == ' ') {
			i++;
		}
		
		return (i < length) ? s.substring(i, length) :  s;
	}
	
	public static String rtrim(String s) {
		if(isEmpty(s)) return "";
		
		int length = s.length();
		int i = length - 1;
		char[] chars = new char[length];
		s.getChars(0, length - 1, chars, 0);
		
		while(i >= 0 && chars[i] == ' ') {
			i--;
		}
		return s.substring(0, i + 1);
	}
	
	public static void main(String[] args) {
		System.out.println(rtrim(" a").length());
	}
}
