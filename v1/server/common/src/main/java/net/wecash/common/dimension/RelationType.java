package net.wecash.common.dimension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.wecash.common.mongo.model.SnsDataBean;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;

/**
 * @author franklin.li
 * 
 */
public class RelationType {
	
	public static Map<Long, List<String>> getRelations(List<Long> userIds, List<SnsDataBean> asbs){
		Map<Long, List<String>> relationMap = new HashMap<Long, List<String>>();
		for(SnsDataBean sdb : asbs){
			relationMap.put(sdb.getUserId(), getRelation(sdb));
		}
		return relationMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<String> getRelation(SnsDataBean sdb){
		List<String> friends = new ArrayList<String>();
		List<Map> list = sdb.getBilateralFriends();
		if(list != null){
			for(Map m : list){
				String uid = MapUtils.getString(m, "uid");
				if(uid != null){
					friends.add(uid);
				}
			}
		}
		return friends;
	}
	
	@SuppressWarnings("unchecked")
	public static int getScore(List<String> selfRelation, List<String> targetRelation){
		int score = 0;
		if(selfRelation == null || targetRelation == null){
			return 0;
		}
		List<String> result = ListUtils.intersection(selfRelation, targetRelation);
		int count = result.size();
		if(count == 0){
			score = 0;
		}else if(count < 5 && count > 0){
			score = 50;
		}else{
			score = 10;
		}
		return score;
	}
}
