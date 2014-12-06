package net.wecash.common.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.wecash.common.jackson.ObjectMapperFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MysqlQueryUtil {
	private static final String[] ignoreStr = {"updateTime", "createTime"};
	/**
	 * 
	 * @param o
	 * @param whereStr,修改字段
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getSingleUpdateHql(Object o,String whereStr){
		StringBuffer sb = new StringBuffer();
		String hqlObjectName = "o";
		sb.append("update ");
		sb.append(o.getClass().getName()).append(" ");
		sb.append(hqlObjectName).append(" set ");
		ObjectMapper mapper = new ObjectMapperFactory().getMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		Map<String, Object> m = mapper.convertValue(o, Map.class);
		if(m != null && m.size() > 0){
			Iterator<String> i = m.keySet().iterator();
			Object id = null;
			List<String> ignoreList = Arrays.asList(ignoreStr);
			while(i.hasNext()){
				String str = i.next();
				if(!ignoreList.contains(str)){
					if(!str.equalsIgnoreCase(whereStr) && m.get(str) != null){
						sb.append(" ").append(hqlObjectName).append(".").append(str).append("='").append(m.get(str)).append("',");
					}else{
						id = m.get(str);
					}
				}
			}
			if(id == null){
				return null;
			}else{
				sb.deleteCharAt(sb.length() - 1);
				sb.append(" where ").append(hqlObjectName).append("."+whereStr).append("='").append(id).append("'");
				return sb.toString();
			}
		}else{
			return null;
		}
	}
	public static String getMutiLikeSql(String sql, String key, List<String> names){
		StringBuffer sb = new StringBuffer();
		if(names != null && key != null){
			sb.append(sql).append(" where ");
			for(String s : names){
				sb.append(key).append(" LIKE '%").append(s).append("%'").append(" OR ");
			}
			sb.delete(sb.length() - 3, sb.length() - 1);
		}
		return sb.toString();
	}
	public static String getMutiSql(String sql,String key,List<String>names){
		StringBuffer sb=new StringBuffer();
		if(names != null && key != null){
			sb.append(sql).append(" where ");
			sb.append(key).append(" in (" + getInStringList(names) + ")");
		}
		return sb.toString();
	}
	@SuppressWarnings("rawtypes")
	public static String getInIntegerList(List o){
		if(o == null || o.size() == 0){
			return null;
		}else{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < o.size(); i++) {
				if (i != o.size() - 1) {
					sb.append(o.get(i) + ",");
				} else {
					sb.append(o.get(i));
				}

			}
			String s = sb.toString();
			return s;
		}
	}
	@SuppressWarnings("rawtypes")
	public static String getInStringList(List o){
		if(o == null || o.size() == 0){
			return null;
		}else{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < o.size(); i++) {
				if (i != o.size() - 1) {
					sb.append("'").append(o.get(i)).append("',");
				} else {
					sb.append("'").append(o.get(i)).append("'");
				}

			}
			String s = sb.toString();
			return s;
		}
	}
}
