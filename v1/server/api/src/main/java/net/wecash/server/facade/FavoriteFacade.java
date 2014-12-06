package net.wecash.server.facade;

import java.util.Date;
import java.util.List;

import net.wecash.common.service.HashCacheNames;
import net.wecash.common.util.DateUtils;
import net.wecash.server.bean.UserCollectBean;
import net.wecash.server.favorite.dao.FavoriteDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class FavoriteFacade {

	@Autowired
	FavoriteDAO favoriteDAO;
	private String userCacheName = HashCacheNames.USER;
	@Autowired
	RedisTemplate redisTemplate;
	
/*	public boolean isCollect(Long id) {
		return favoriteDAO.isCollect(id);
	}*/

	public boolean isFavorite(Long userId, Long targetId){
		return favoriteDAO.isFavorite(userId, targetId);
	}
	
	public void save(Long id, Long targetId) {
		favoriteDAO.save(id, targetId);
	}
	public List<UserCollectBean> getAll(Long id, Integer cursor, Integer limit) {
		List<UserCollectBean> list= favoriteDAO.getFavorites(id, cursor, limit);
		for (UserCollectBean userDetail : list) {
			if(userDetail != null && (userDetail).getBirthday() != null){
				userDetail.setConstellation(DateUtils.getConstellation(DateUtils.formatDate(userDetail.getBirthday())));
				userDetail.setAge(DateUtils.getDateGap(userDetail.getBirthday(), new Date()));
			}
		}
		return list;
	}

	public boolean getById(Long id, Long targetId) {
		return favoriteDAO.isFavorite(id,targetId);
	}

	public void delete(Long id, Long targetId) {
		favoriteDAO.delete(id,targetId);
	}
	public void delete(Long id) {
		favoriteDAO.delete(id);
	}
}
