package net.wecash.server.facade;

import java.util.Date;

import net.wecash.common.service.HashCacheNames;
import net.wecash.server.mysql.model.Token;
import net.wecash.server.user.dao.TokenDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TokenFacade {
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	TokenDAO tokenDAO;
	private String tokenCacheName = HashCacheNames.TOKEN;
	
	
	@SuppressWarnings("unchecked")
	public void addToken(Token token){
		try {
			Assert.notNull(token);
			Assert.notNull(token.getToken());
			Assert.notNull(token.getUserId());
			token.setCreateTime(new Date());
			Token oldToken = tokenDAO.getToken(token.getUserId(), token.getType());
			if(oldToken != null){
				redisTemplate.opsForHash().delete(tokenCacheName, oldToken.getToken());;
			}
			tokenDAO.addToken(token);
			redisTemplate.opsForHash().put(tokenCacheName, token.getToken(), token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteToken(String token){
		try {
			Assert.notNull(token);
			tokenDAO.deleteToken(token);
			redisTemplate.opsForHash().delete(tokenCacheName, token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deleteToken(Long id){
		try {
			Token t = tokenDAO.getToken(id, null);
			tokenDAO.deleteToken(id);
			redisTemplate.opsForHash().delete(tokenCacheName, t.getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Token getToken(Long userId){
		return tokenDAO.getToken(userId, null);
	}
	
}
