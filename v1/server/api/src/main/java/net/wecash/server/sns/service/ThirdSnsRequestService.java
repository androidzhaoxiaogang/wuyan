package net.wecash.server.sns.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.util.IOUtils;
import net.wecash.common.util.NetUtils;
import net.wecash.common.util.Utilities;
import net.wecash.server.sns.dao.ThirdSnsRequestDAO;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ThirdSnsRequestService implements ThirdSnsRequestDAO{

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
	
	@Value("${api.weibo.friends.bilateral}")
	private String weiboFriendsBilateral;
	
	@Value("${default.icon}")
	private String defaultIcon;
    
	@SuppressWarnings("rawtypes")
	@Override
	public boolean validateWeiboToken(String uid, String third_token) {
    	boolean validate = false;
    	String url = weiboTokenValidateUrl + "?access_token="+third_token;
    	String result = null;
    	Map validateResult = null;
    	try {
			result = template.postForObject(url, null, String.class);
			validateResult = mapper.readValue(result, Map.class);
	    	if(validateResult != null && MapUtils.getString(validateResult, "uid").equals(uid)){
	    		validate = true;
	    	}
		} catch (Exception e) {
			throw new ErrorCodeException(new Error(ErrorCode.THIRD_TOKEN_UNAUTHORIZED));
		}
    	return validate;
    }
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public Map<String, Object> getWeiboInfo(String uid, String third_token){
		Map<String, Object> result = null;
    	try {
    		result = template.getForObject(weiboUserShowUrl + "?access_token=" + third_token + "&uid=" + uid, Map.class);
		} catch (Exception e) {
			throw new ErrorCodeException(new Error(ErrorCode.THIRD_TOKEN_UNAUTHORIZED));
		}
		return result;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUserTagsFromWeibo(String uid, String third_token){
		List<Map> result = null;
    	try {
    		result = template.getForObject(weiboTagsUrl + "?access_token=" + third_token + "&uid=" + uid, List.class);
		} catch (Exception e) {
			throw new ErrorCodeException(new Error(ErrorCode.THIRD_TOKEN_UNAUTHORIZED));
		}
		return result;
	}
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUserTimeline(String uid, String third_token){
		List<Map> list = null;
		try{
			String text = IOUtils.stream2String(NetUtils.getMethod(weiboUserTimelineUrl + "?access_token=" + third_token + "&uid=" + uid + "&count=200&page=1", null));
			Map map = mapper.readValue(text, LinkedHashMap.class);
			list = (List)map.get("statuses");
		}catch(Exception e){
			throw new ErrorCodeException(new Error(ErrorCode.THIRD_TOKEN_UNAUTHORIZED));
		}
		return list;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getUserFavorites(String uid, String third_token){
		List<Map> list = null;
		try {
			String text = IOUtils.stream2String(NetUtils.getMethod(weiboUserFavoritesUrl + "?access_token=" + third_token, null));
			Map map = mapper.readValue(text, LinkedHashMap.class);
			list = (List) map.get("favorites");
		} catch (Exception e) {
			throw new ErrorCodeException(new Error(ErrorCode.THIRD_TOKEN_UNAUTHORIZED));
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getFriendsBilateral(String uid, String third_token){
		List<Map> list = new ArrayList<>();
		try {
			String text = IOUtils.stream2String(NetUtils.getMethod(weiboFriendsBilateral + "?access_token=" + third_token, null));
			Map map = mapper.readValue(text, LinkedHashMap.class);
			if(map != null){
				Map friend = new HashMap<>();
				list = (List) map.get("users");
				for(Map user : list){
					friend.put("uid", user.get("id").toString());
					friend.put("name", user.get("name").toString());
					friend.put("profile_image_url", user.get("profile_image_url").toString());
				}
			}
		} catch (Exception e) {
			throw new ErrorCodeException(new Error(ErrorCode.THIRD_TOKEN_UNAUTHORIZED));
		}
		return list;
	}
	
	@Override
	public byte[] getImage(String url){
		try {
			return template.getForObject(url, byte[].class);
		} catch (Exception e) {
			e.printStackTrace();
			return Utilities.hexStringToBytes(defaultIcon);
		}
	}
}
