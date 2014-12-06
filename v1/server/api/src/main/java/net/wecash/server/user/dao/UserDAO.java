package net.wecash.server.user.dao;

import java.util.List;

import net.wecash.server.bean.MatchRequireBean;
import net.wecash.server.bean.UserDetail;
import net.wecash.server.mysql.model.User;
import net.wecash.server.mysql.model.UserInfo;

public interface UserDAO {

	public void updateUser(User user);

	public void addUser(User user);

	public User getUser(Long userId);

	public void deleteUser(Long userId);

	public UserDetail getUserDetail(Long id);

	public List<User> getUsers(List<Long> userIds);

	public List<UserDetail> getUserDetails(List<Long> ids);

	public MatchRequireBean getMatchRequire(Long id);

	public UserDetail getUserDetailInfo(Long id);

	public UserInfo getUserInfo(Long user_id);

}
