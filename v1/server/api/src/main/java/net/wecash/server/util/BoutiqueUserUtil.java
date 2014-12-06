/**
 * 
 */
package net.wecash.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author franklin.li
 * 
 */
public class BoutiqueUserUtil {
	public static final Integer userId1 = 10565;
	public static final Integer userId2 = 10566;
	
	public static void removeBoutiqueUser(List<Long> userIds){
		userIds.remove(userId1);
		userIds.remove(userId2);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> getBoutiqueUsers(){
		List<Map> requestList = new ArrayList<Map>();
		Map<String, Object> userMap1 = new HashMap<String, Object>();
		userMap1.put("name", "Kevin");
		userMap1.put("gender", "m");
		userMap1.put("description", "找干干净净舒适的阳光房，你我共同的家");
		userMap1.put("icon", "53e0c5a9ad074c10fa61a0c3");
		String[] images1 = {"53e0c5a9ad074c10fa61a0c6","53e0c5aaad074c10fa61a0c9","53e0c5aaad074c10fa61a0cc"};
		userMap1.put("image", images1);
		Map funnyTag1 = new HashMap<>();
		funnyTag1.put("啥？你们都有洁癖！？", 3);
		funnyTag1.put("你和TA实在同一个垃圾桶被捡到的！", 5);
		funnyTag1.put("你们都很逗比", 8);
		userMap1.put("funnyTags", funnyTag1);
		userMap1.put("userId", userId1);
		userMap1.put("state", 1);
		requestList.add(userMap1);
		Map<String, Object> userMap3 = new HashMap<String, Object>();
		userMap3.put("name", "Jennifer");
		userMap3.put("gender", "f");
		userMap3.put("description", "吃货一枚，做菜超棒");
		userMap3.put("icon", "53e0c51aad074c10fa61a0b5");
		String[] images3 = {"53e0c51aad074c10fa61a0b8","53e0c51aad074c10fa61a0bb","53e0c51bad074c10fa61a0be"};
		userMap3.put("image", images3);
		Map funnyTag3 = new HashMap<>();
		funnyTag3.put("小狗狗", 20);
		funnyTag3.put("舌尖上的屋檐", 14);
		funnyTag3.put("任何一个角落，都是风景", 12);
		userMap3.put("funnyTags", funnyTag3);
		userMap3.put("userId", userId2);
		userMap3.put("state", 1);
		requestList.add(userMap3);
		return requestList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getBoutiqueUser(Long targetId){
		Map<String, Object> userMap = new HashMap<String, Object>();
		if(targetId == (long)userId2){
			userMap.put("name", "Jennifer");
			userMap.put("gender", "f");
			userMap.put("userId", userId2);
			userMap.put("age", 24);
			userMap.put("constellation", "白羊座");
			userMap.put("place", "");
			userMap.put("minPrice", 1000);
			userMap.put("maxPrice", 3000);
			userMap.put("occupation", 6);
			userMap.put("habit", 1);
			userMap.put("description", "吃货一枚，做菜超棒");
			userMap.put("icon", "53e0c51aad074c10fa61a0b5");
			String[] tags = {"夜猫子","友好","吃货","音乐狂","纠结不已"};
			userMap.put("tags", tags);
			Map<String, Object> score = new HashMap<String, Object>();
			score.put("habit", 66);
			score.put("birthday", 90);
			score.put( "interest", 90);
			score.put("socialRelation", 80);
			score.put("degree", 90);
			score.put("occupation", 75);
			score.put( "total", 86);
			userMap.put("score", score);
			List<Map> friends = new ArrayList<>();
			Map friend = new HashMap<>();
			friend.put("uid", "1808624312");
			friend.put("name", "章泽天");
			friend.put("profileImageUrl", "http://tp1.sinaimg.cn/1808624312/50/1289213709/0");
			friends.add(friend);
			userMap.put("friends", friends);
		}else{
			userMap.put("name", "Kevin");
			userMap.put("gender", "m");
			userMap.put("description", "找干干净净舒适的阳光房，你我共同的家");
			userMap.put("icon", "53e0c5a9ad074c10fa61a0c3");
			userMap.put("age", 23);
			userMap.put("constellation", "摩羯座");
			userMap.put("place", "");
			userMap.put("minPrice", 1000);
			userMap.put("maxPrice", 2000);
			userMap.put("occupation", 5);
			userMap.put("userId", userId1);
			userMap.put("habit", 0);
			String[] tags = {"篮球","旅游","电子竞技","音乐狂","分享"};
			userMap.put("tags", tags);
			Map<String, Object> score = new HashMap<String, Object>();
			score.put("habit", 86);
			score.put("birthday", 80);
			score.put( "interest", 86);
			score.put("socialRelation", 70);
			score.put("degree", 99);
			score.put("occupation", 80);
			score.put( "total", 85);
			userMap.put("score", score);
			List<Map> friends = new ArrayList<>();
			Map friend1 = new HashMap<>();
			friend1.put("uid", "2219088342");
			friend1.put("name", "全球创意");
			friend1.put("profileImageUrl", "http://tp3.sinaimg.cn/2219088342/50/40021469058/1");
			friends.add(friend1);
			Map friend2 = new HashMap<>();
			friend2.put("uid", "1786268803");
			friend2.put("name", "Engourdi");
			friend2.put("profileImageUrl", "http://tp4.sinaimg.cn/1786268803/50/5696194590/1");
			friends.add(friend2);
			userMap.put("friends", friends);
		}
		return userMap;
	}
	public static void main(String []args){
		Long l = new Long(10565L);
		System.out.println(getBoutiqueUser(l));
	}
}
