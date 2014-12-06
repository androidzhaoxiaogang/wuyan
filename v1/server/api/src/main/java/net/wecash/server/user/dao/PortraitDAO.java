package net.wecash.server.user.dao;

import java.util.List;

import net.wecash.server.bean.UserPortraitResultBean;

/**
 * @author franklin.li
 * 
 */
public interface PortraitDAO {

	public List<UserPortraitResultBean> getPortrait(Long userId, List<Long> targetUserIds,
			List<Integer> degree, List<Integer> occupation, Float personality, int state, Integer limit);

	public List<Object> getUserId(String province, String city, String area,
			String landMark, Integer minPrice, Integer maxPrice, int state, int limit);

	public List<Object> getUserId(String province, int state, int limit);

}
