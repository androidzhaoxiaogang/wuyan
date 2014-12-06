/**
 * 
 */
package net.wecash.server.util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author franklin.li
 * 
 */
public class ReturnUtil {
	private static ObjectMapper generalMapper = new ObjectMapper();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map formatNullObj(Object o){
		Map<String, Object> map = null;
		if(o != null){
			map = generalMapper.convertValue(o, Map.class);
			Field[] fields = o.getClass().getDeclaredFields();
			for(Field f : fields){
				String key = f.getName();
				if(map.get(key) == null){
					String valueType = f.getGenericType().toString();
					if(valueType.equals("class java.lang.Long")
							||valueType.equals("class java.lang.Integer")
							|| valueType.equals("class java.lang.Short")){
						map.put(key, 0);
					}else if(valueType.equals("class java.lang.Float")
							||valueType.equals("class java.lang.Double")){
						map.put(key, 1f);
					}else if(valueType.equals("class java.lang.Boolean")){
						map.put(key, false);
					}else if(valueType.equals("class java.util.Date")){
						map.put(key, "");
					}else if(valueType.equals("class java.lang.Character")){
						map.put(key, "");
					}else if(valueType.equals("class java.lang.String")){
						map.put(key, "");
					}else if(valueType.equals("java.util.List<java.lang.String>")){
						map.put(key, new ArrayList<List>());
					}else if(valueType.equals("java.util.List<java.util.Map>")){
						map.put(key, new ArrayList<Map>());
					}else if(valueType.equals("interface java.util.Map")){
						map.put(key, new HashMap<>());
					}else{
						map.put(key, "");
					}
				}
			}
		}else{
			map = new HashMap<>();
		}
		return map;
	}
}
