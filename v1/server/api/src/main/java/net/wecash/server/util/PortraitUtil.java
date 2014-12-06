package net.wecash.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.wecash.common.mongo.model.SnsDataBean;
import net.wecash.server.analyzer.score.AnalyzerScoreBean;
import net.wecash.server.bean.UserImgBean;
import net.wecash.server.bean.UserPortraitResultBean;
import net.wecash.server.interest.InterestEncodeBean;
import net.wecash.server.mysql.model.UserImg;

public class PortraitUtil {
	public static String[] userPortrait = {"personality", "habit", "degree", "occupation"};
	public static String[] roomAddr = {"minPrice", "maxPrice", "lat", "lng", "province", "city", "area", "landmark"};
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getCalPortrait(Map map){
		Map portraitMap = new HashMap<>();
		for(String s : userPortrait){
			if(map.containsKey(s)){
				portraitMap.put(s, map.get(s));
			}
		}
		return portraitMap;
	}
	
	public static String getDefaultInterestCode(){
		return "0";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getCalRoomAddr(Map map){
		Map roomAddrMap = new HashMap<>();
		for(String s : roomAddr){
			if(map.containsKey(s)){
				roomAddrMap.put(s, map.get(s));
			}
		}
		return roomAddrMap;
	}
	
	public static List<Long> getUserId(List<UserPortraitResultBean> uprbs){
		List<Long> list = new ArrayList<>();
		for(UserPortraitResultBean uprb : uprbs){
			if(uprb.getUserId() != null && uprb.getUserId() != 0){
				list.add((long)uprb.getUserId());
			}
		}
		return list;
	}
	
	public static Map<Long, UserPortraitResultBean> getUserPortraitMap(List<UserPortraitResultBean> uprbs){
		Map<Long, UserPortraitResultBean> map = new HashMap<Long, UserPortraitResultBean>();
		for(UserPortraitResultBean u : uprbs){
			if(u.getUserId() != null){
				map.put(new Long((long)u.getUserId()), u);
			}
		}
		return map;
	}
	
	public static Map<Long, InterestEncodeBean> getUserInterests(List<InterestEncodeBean> iebs){
		Map<Long, InterestEncodeBean> map = new HashMap<>();
		for(InterestEncodeBean ieb : iebs){
			if(ieb.getUserId() != null){
				map.put(ieb.getUserId(), ieb);
			}
		}
		return map;
	}
	
	public static List<Long> getTargetIds(List<AnalyzerScoreBean> asbs){
		List<Long> list = new ArrayList<Long>();
		for(AnalyzerScoreBean asb : asbs){
			list.add(asb.getTargetId());
		}
		return list;
	}
	
	public static Map<Long, List<String>> getUserImgMap(List<UserImgBean> imgs){
		Map<Long, List<String>> tempImgs = new HashMap<>();
		for(UserImgBean ui : imgs){
			Long userId = (long)ui.getUserId();
			if(tempImgs.containsKey(userId)){
				tempImgs.get(userId).add(ui.getImage());
			}else{
				List<String> imgList = new ArrayList<>();
				imgList.add(ui.getImage());
				tempImgs.put(userId, imgList);
			}
		}
		return tempImgs;
	}
	
	public static List<String> getUserImg(List<UserImg> imgs){
		List<String> img = new ArrayList<>();
		if(imgs != null){
			for(UserImg ui : imgs){
				img.add(ui.getImage());
			}
		}
		return img;
	}
	
	public static Map<Long, String> getUserIcon(List<UserImgBean> imgs){
		Map<Long, String> tempImgs = new HashMap<>();
		for(UserImgBean ui : imgs){
			tempImgs.put((long)ui.getUserId(), ui.getImage());
		}
		return tempImgs;
	}
	
	public static Map<Long, SnsDataBean> getSnsDataMap(List<SnsDataBean> sdbs){
		Map<Long, SnsDataBean> sdm = new HashMap<>();
		for(SnsDataBean sdb : sdbs){
			sdm.put(sdb.getUserId(), sdb);
		}
		return sdm;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Map> getSnsDataFriensMap(List<Map> friends){
		Map<String, Map> map = new HashMap<>();
		for(Map m : friends){
			if(m.get("uid") != null){
				map.put(m.get("uid").toString(), m);
			}
		}
		return map;
	}
	@SuppressWarnings("rawtypes")
	public static List<String> getSnsDataFriensNameList(List<Map> friends){
		List<String> list = new ArrayList<>();
		for(Map m : friends){
			if(m.get("uid") != null){
				list.add(m.get("uid").toString());
			}
		}
		return list;
	}
}
