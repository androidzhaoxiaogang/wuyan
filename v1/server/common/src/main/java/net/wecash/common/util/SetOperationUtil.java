package net.wecash.common.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author xkk
 *
 */
public class SetOperationUtil {
	/**
	 * 交集
	 * @param a
	 * @param b
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set union(Set a, Set b){
		Set result = new HashSet();
		result.clear();
		result.addAll(a);
		result.retainAll(b);
		return result;
	}
	
	/**
	 * 并集
	 * @param a
	 * @param b
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set intersection(Set a, Set b){
		Set result = new HashSet();
		result.clear();
		result.addAll(a);
		result.addAll(b);
		return result;
	}
	
	/**
	 * 差集
	 * @param a
	 * @param b
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Set subtract(Set a, Set b){
		Set result = new HashSet();
		result.clear();
		result.addAll(a);
		result.removeAll(b);
		return result;
	}
}
