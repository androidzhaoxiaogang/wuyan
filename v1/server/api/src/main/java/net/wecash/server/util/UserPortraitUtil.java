package net.wecash.server.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.wecash.common.util.SetOperationUtil;

public class UserPortraitUtil {
	public static String[] reCaList = {"gender", "birthday", "state", "personality", "habit", "degree", "occupation"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean reCalculateUserCharacteristic(Map map){
		boolean reCal = false;
		Set sourceSet = map.keySet();
		Set reCalSet = new HashSet(Arrays.asList(reCaList));
		Set resultSet = SetOperationUtil.union(reCalSet, sourceSet);
		if(resultSet != null && resultSet.size() > 0){
			reCal = true;
		}
		return reCal;
	}
}
