package net.wecash.server.user.dao;

import java.util.List;

import net.wecash.server.bean.UserTagReturnBean;
import net.wecash.server.mysql.model.UserTag;

public interface UserTagDao {

	public List<UserTagReturnBean> getTagInfo(Long userid);

	public void saveUserTag(UserTag userTag);

	public void delete(UserTag userTag);

	
}
