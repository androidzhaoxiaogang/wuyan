package net.wecash.server.facade;

import java.util.List;

import net.wecash.server.bean.UserTagReturnBean;
import net.wecash.server.mysql.model.Tag;
import net.wecash.server.mysql.model.UserTag;
import net.wecash.server.tag.dao.TagDAO;
import net.wecash.server.user.dao.UserTagDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserTagFacade {
	@Autowired
	UserTagDao userTagDao;
	@Autowired
	TagDAO tagDao;

	public List<UserTagReturnBean> getTagInfo(Long userid) {
		Assert.notNull(userid);
		return userTagDao.getTagInfo(userid);
	}

	public void delete(UserTag userTag) {
		Assert.notNull(userTag);
		userTagDao.delete(userTag);

	}

	public void delete(Long id, List<Long> tags) {
		for (Long l : tags) {
			UserTag userTag = new UserTag();
			userTag.setTagId(l);
			userTag.setUserId(id);
			userTagDao.delete(userTag);
		}
	}

	public Tag getTag(Long tag) {
		return tagDao.getTag(tag);
	}

	public void save(Long id, Long tag) {
		UserTag userTag = new UserTag();
		userTag.setTagId(tag);
		userTag.setUserId(id);
		userTagDao.saveUserTag(userTag);
	}
}
