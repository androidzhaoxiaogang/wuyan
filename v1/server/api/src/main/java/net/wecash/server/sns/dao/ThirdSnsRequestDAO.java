package net.wecash.server.sns.dao;

import java.util.List;
import java.util.Map;




public interface ThirdSnsRequestDAO {
	public boolean validateWeiboToken(String uid, String access_token);
	
	public Map<String, Object> getWeiboInfo(String uid, String access_token);

	public byte[] getImage(String url);

	public List<Map> getUserTagsFromWeibo(String uid, String access_token);

	public List<Map> getUserTimeline(String uid, String access_token);

	public List<Map> getUserFavorites(String uid, String access_token);
}
