package net.wecash.server.auth;

import net.wecash.common.auth.ClientInfo;
import net.wecash.common.auth.RoleInfo;
import net.wecash.common.exception.Error;
import net.wecash.common.exception.ErrorCode;
import net.wecash.common.exception.ErrorCodeException;
import net.wecash.common.service.HashCacheNames;
import net.wecash.server.mysql.model.Token;
import net.wecash.server.user.dao.TokenDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author xkk
 *
 */
@Component
public class AuthValidater {
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	TokenDAO tokenDAO;
	/**
	 * validate user's token
	 * @param token
	 * @return
	 */
	private String tokenCacheName = HashCacheNames.TOKEN;
	@SuppressWarnings("unchecked")
	public Token validateToken(String token){
		boolean refreshCache = false;
		Object o = redisTemplate.opsForHash().get(tokenCacheName, token);
		Token resultBean = null;
		if(o != null){
			resultBean = (Token)o;
		}else{
			refreshCache = true;
			resultBean = tokenDAO.getToken(token);
		}
		if(resultBean == null){
			throw new ErrorCodeException(new Error(ErrorCode.USER_LOGIN_REQUIRED));
		}
		if(refreshCache){
			redisTemplate.opsForHash().put(tokenCacheName, token, resultBean);
		}
		/*else if((resultBean.getCreateTime() + resultBean.getExpiresIn()) < DateUtils.getUTC()){
		
			throw new ErrorCodeException(new Error(ErrorCode.EXPIRED_TOKEN));
		}*/
		return resultBean;
	}
	
	/**
	 * some method only validate himself
	 * @param username
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Token validateToken(Long userId, String token){
		boolean refreshCache= false;
		Object o = redisTemplate.opsForHash().get(tokenCacheName, token);
		Token resultBean = null;
		if(o != null){
			resultBean = (Token)o;
		}else{
			refreshCache = true;
			resultBean = tokenDAO.getToken(token);
		}
		if(resultBean == null){
			throw new ErrorCodeException(new Error(ErrorCode.USER_LOGIN_REQUIRED));
		}else if(resultBean.getUserId() != userId || resultBean.getType() != RoleInfo.NORMAL){
			throw new ErrorCodeException(new Error(ErrorCode.PERMISSION_DENIED));
		}
		if(refreshCache){
			redisTemplate.opsForHash().put(tokenCacheName, token, resultBean);
		}
		return resultBean;
	}
	
	/**
	 * validate admin's token
	 * @param token
	 * @return
	 */
	public Token validateSystemToken(String token){
		Token tokenBean = validateToken(token);
		if(tokenBean.getType() != RoleInfo.SYSTEM){
			throw new ErrorCodeException(new Error(ErrorCode.PERMISSION_DENIED)); 
		}
		return tokenBean;
	}
	
	public int validateClient(String clientId, String clientSecret){
		int clientType = 3;
		if(ClientInfo.validateBackendClient(clientId, clientSecret)){
			clientType = 0;
		}else if(ClientInfo.validateIOSClient(clientId, clientSecret)){
			clientType = 4;
		}else if(ClientInfo.validateAndroidClient(clientId, clientSecret)){
			clientType = 3;
		}else if(ClientInfo.validateWebClient(clientId, clientSecret)){
			clientType = 2;
		}else{
			throw new ErrorCodeException(new Error(ErrorCode.CLIENT_TYPE_UNAUTHORIZED));
		}
		return clientType;
	}
}
