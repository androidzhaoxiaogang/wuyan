/*package net.wecash.oauth2.schedule;

import net.wecash.common.auth.oauth2.TokenInfo;
import net.wecash.common.model.Token;
import net.wecash.common.util.DateUtils;
import net.wecash.oauth2.dao.OAuth2DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

*//**
 * 
 * @author xkk
 *
 *//*
@Component
public class TokenCleaner {
	@Autowired
	private OAuth2DAO oAuth2DAO;
	@Autowired
	RedisTemplate<Object, Token> redisTemplate;
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(TokenCleaner.class);
	
	@Scheduled(fixedRate = 5000, initialDelay = 30000)
	public void cleanToken(){
//		redisTemplate.opsForHash().delete(key, hashKey);
		oAuth2DAO.removeExpiredToken(DateUtils.getUTC() - TokenInfo.TokenExpiresIn);
		
	}
}
*/