/**
 * 
 */
package net.wecash.server.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.wecash.common.jackson.ObjectMapperFactory;
import net.wecash.common.util.SetOperationUtil;
import net.wecash.server.bean.region.zh.bean.RegionBean;
import net.wecash.server.mysql.model.RoomInfo;
import net.wecash.server.mysql.model.User;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author franklin.li
 * 
 * 增删改查辅助类
 */
public class CRUDUtil {
	private static ObjectMapper mapper = new ObjectMapperFactory().getMapper();
	public static String[] region = {"province", "city", "area"/*, "landmark"*/};
	
	@SuppressWarnings("rawtypes")
	public static boolean canUpdateRegion(Object o){
		Map map = new HashMap<>();
		int count = 0;
		for(String s : region){
			if(map.containsKey(s)){
				count ++;
			}
		}
		return (count == 0 || count == 3) ? true : false;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean canUpdate(Object o){
		Map m = mapper.convertValue(o, Map.class);
		return  (m != null && m.size() > 0) ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T checkUpdateValues(Object updateObj, Object oldObj, Class<T> type){
		T t = null;
		try {
			Map<String, Object> updateMap = mapper.convertValue(updateObj, Map.class);
			Map<String, Object> oldMap = mapper.convertValue(oldObj, Map.class);
			Map<String, Object> realMap = new HashMap<String, Object>();
			if(oldMap != null && updateObj != null){
				Set<String> set = updateMap.keySet();
				for(String s : set){
					Object oldValue = oldMap.get(s);
					Object newValue = updateMap.get(s);
					if(oldValue == null || !oldValue.equals(newValue)){
						realMap.put(s, newValue);
					}
				}
				t =  mapper.convertValue(realMap, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public static void formatUserUpdateValues(User user){
		boolean hasError = false;
		if(user.getPersonality() != null){
			if(user.getPersonality() > 1f || user.getPersonality() < 0f){
				user.setPersonality(0.5f);
				hasError = true;
			}
		}
		if(user.getHabit() != null){
			if(user.getHabit() > 3 || user.getHabit() < 0){
				user.setHabit(0);
				hasError = true;
			}
		}
		if(user.getDegree() != null){
			if(user.getDegree() > 4 || user.getDegree() < 0){
				user.setDegree(0);
				hasError = true;
			}
		}
		if(user.getOccupation() != null){
			if(user.getOccupation() > 6 || user.getOccupation() < 0){
				user.setOccupation(0);
				hasError = true;
			}
		}
		if(user.getState() != null){
			if(user.getState() > 2 || user.getState() < 0){
				user.setState(0);
				hasError = true;
			}
		}
		if(hasError){
			System.out.println("user value error:" + user);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T intersectionUpdateValues(Object updateObj, Object oldObj, Class<T> type){
		T t = null;
		try {
			Map<String, Object> updateMap = mapper.convertValue(updateObj, Map.class);
			Map<String, Object> oldMap = mapper.convertValue(oldObj, Map.class);
			Map<String, Object> realMap = new HashMap<String, Object>();
			if(oldMap != null && updateMap != null){
				Set<String> set1 = updateMap.keySet();
				Set<String> set2 = oldMap.keySet();
				List<String> list = new ArrayList<>(SetOperationUtil.intersection(set1, set2));
				for(String s : list){
					Object oldValue = oldMap.get(s);
					Object newValue = updateMap.get(s);
					if(oldValue == null && newValue != null){
						realMap.put(s, newValue);
					}else if(oldValue != null && newValue == null){
						realMap.put(s, oldValue);
					}else if(oldValue != null && newValue != null){
						realMap.put(s, newValue);
					}
				}
				t =  mapper.convertValue(realMap, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	public static void convertRoomInfo(RegionBean rb, RoomInfo roomInfo){
		roomInfo.setProvince(rb.getProvince());
		roomInfo.setArea(rb.getArea());
		roomInfo.setCity(rb.getCity());
		roomInfo.setLandmark(rb.getLandmark());
	}
}
