package net.wecash.server.favorite.dao;

import java.util.List;

import net.wecash.server.bean.UserCollectBean;

public interface FavoriteDAO {

	void save(Long userId, Long toUserId);

	List<UserCollectBean> getFavorites(Long userId, Integer cursor, Integer i);


	void delete(Long userId, Long targetId);

	boolean isFavorite(Long userId, Long targetId);

	void delete(long id);

	
}
