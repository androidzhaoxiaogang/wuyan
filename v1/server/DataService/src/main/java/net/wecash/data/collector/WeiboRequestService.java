package net.wecash.data.collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.wecash.common.util.IOUtils;
import net.wecash.common.util.NetUtils;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WeiboRequestService{

	@Autowired
    private RestTemplate template;
    @Autowired
    private ObjectMapper mapper;
    
    @Value("${api.weibo.token.validate}")
    private String weiboTokenValidateUrl;
    
    @Value("${api.weibo.user.show}")
    private String weiboUserShowUrl;
    
    @Value("${api.weibo.user.tags}")
    private String weiboTagsUrl;
    
	@Value("${api.weibo.user.timeline}")
	private String weiboUserTimelineUrl;
	
	@Value("${api.weibo.user.favorite}")
	private String weiboUserFavoritesUrl;
	
	@Value("${api.weibo.friends.bilateral.ids}")
	private String weiboFriendsBilateralIds;
    
	@Value("${api.weibo.friends.bilateral}")
	private String weiboFriendsBilateral;
	
	private static Logger logger = LoggerFactory.getLogger(WeiboRequestService.class);
	
	@SuppressWarnings("rawtypes")
	public boolean validateWeiboToken(String uid, String third_token) {
    	boolean validate = false;
    	String url = weiboTokenValidateUrl + "?access_token="+third_token;
    	String result = null;
    	Map validateResult = null;
    	try {
			result = template.postForObject(url, null, String.class);
			if(result != null){
				validateResult = mapper.readValue(result, Map.class);
		    	if(validateResult != null && MapUtils.getString(validateResult, "uid").equals(uid)){
		    		validate = true;
		    	}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
    	return validate;
    }
	
	@SuppressWarnings({ "unchecked" })
	public Map<String, Object> getUserInfo(String uid, String third_token){
		Map<String, Object> result = null;
    	try {
    		result = template.getForObject(weiboUserShowUrl + "?access_token=" + third_token + "&uid=" + uid, Map.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUserTagsFromWeibo(String uid, String third_token){
		List<Map> result = null;
    	try {
    		result = template.getForObject(weiboTagsUrl + "?access_token=" + third_token + "&uid=" + uid, List.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUserTimeline(String uid, String third_token){
		List<Map> list = null;
		try{
			String text = IOUtils.stream2String(NetUtils.getMethod(weiboUserTimelineUrl + "?access_token=" + third_token + "&uid=" + uid + "&count=200&page=1", null));
			if(text != null){
				Map map = mapper.readValue(text, LinkedHashMap.class);
				list = (List)map.get("statuses");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUserFavorites(String uid, String third_token){
		List<Map> list = null;
		try {
			String text = IOUtils.stream2String(NetUtils.getMethod(weiboUserFavoritesUrl + "?access_token=" + third_token, null));
			if(text != null){
				Map map = mapper.readValue(text, LinkedHashMap.class);
				list = (List) map.get("favorites");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getFriendsBilateral(String uid, String third_token){
		List<Map> result = new ArrayList<>();
		try {
			String text = IOUtils.stream2String(NetUtils.getMethod(weiboFriendsBilateral + "?access_token=" + third_token, null));
			if(text != null){
				Map map = mapper.readValue(text, LinkedHashMap.class);
				if(map != null){
					List<Map> list = (List) map.get("users");
					for(Map user : list){
						Map friend = new HashMap<>();
						friend.put("uid", user.get("id").toString());
						friend.put("name", user.get("name").toString());
						friend.put("profileImageUrl", user.get("profile_image_url").toString());
						result.add(friend);
					}
				}else{
					return null;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public byte[] getImage(String url){
		return template.getForObject(url, byte[].class);
	}
}
