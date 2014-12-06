package net.wecash.server.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.wecash.common.service.HashCacheNames;
import net.wecash.common.util.DateUtils;
import net.wecash.server.bean.MatchRequireBean;
import net.wecash.server.bean.UserDetail;
import net.wecash.server.mysql.model.User;
import net.wecash.server.mysql.model.UserInfo;
import net.wecash.server.user.dao.UserDAO;
import net.wecash.server.util.CRUDUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserFacade {
	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	UserDAO userDAO;
	@Autowired
	ObjectMapper mapper;
	private String userCacheName = HashCacheNames.USER;
	
	@SuppressWarnings("unchecked")
	public User getUser(Long userId){
		Assert.notNull(userId);
		Object o = redisTemplate.opsForHash().get(userCacheName, userId);
		User user = null;
		if(o == null){
			user = userDAO.getUser(userId);
		}else{
			user = (User)o;
		}
		
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUser(List<Long> userIds){
		Assert.notNull(userIds);
		Object o = redisTemplate.opsForHash().multiGet(userCacheName, userIds);
		List<User> users = null;
		if(o == null){
			users = userDAO.getUsers(userIds);
		}else{
			users = (List<User>)o;
		}
		
		return users;
	}
	
	@SuppressWarnings("unchecked")
	public void updateUser(User user){
		Assert.notNull(user);
		CRUDUtil.formatUserUpdateValues(user);
		userDAO.updateUser(user);
		user = userDAO.getUser(user.getId());
		redisTemplate.opsForHash().put(userCacheName, user.getId(), user);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUser(Long userId){
		Assert.notNull(userId);
		redisTemplate.opsForHash().delete(userCacheName, userId);
		userDAO.deleteUser(userId);
	}
	
	@SuppressWarnings("unchecked")
	public void addUser(User user){
		Assert.notNull(user);
		userDAO.addUser(user);
		redisTemplate.opsForHash().put(userCacheName, user.getId(), user);
	}

	public UserDetail getUserDetail(Long id) {
		Assert.notNull(id);
		return userDAO.getUserDetail(id);
	}
	
	public UserDetail getUserDetailInfo(Long id) {
		Assert.notNull(id);
		UserDetail ud = userDAO.getUserDetailInfo(id);
		if(ud != null){
			if(ud.getBirthday() != null){
				ud.setConstellation(DateUtils.getConstellation(DateUtils.formatDate(ud.getBirthday())));
				int age = DateUtils.getDateGap(ud.getBirthday(), new Date());
				ud.setAge(age >= 0 ? age : 0);
			}
			if(ud.getPersonality() != null){
				ud.setPersonality((float)(Math.round(ud.getPersonality()*100))/100);
			}
			if(ud.getLandmark() == null || ud.getLandmark().equals("")){
				ud.setPlace(ud.getArea());
			}else{
				ud.setPlace(ud.getLandmark());
			}
		}
		return ud;
	}
	
	@SuppressWarnings("rawtypes")
	public Float getUserInfoIntegritylevel(Long userId){
		int count = 0;
		try {
			MatchRequireBean mrb = userDAO.getMatchRequire(userId);
			mrb.setUserId(null);
			Map mrbMap = mapper.convertValue(mrb, Map.class);
			count = mrbMap.size();
		} catch (Exception e) {
			return 0f;
		}
		return count/10f;
	}

	public UserInfo getUserInfo(Long user_id) {
		return userDAO.getUserInfo(user_id);
	}
}
