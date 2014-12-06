package net.wecash.server.user.dao;

import java.util.List;

import net.wecash.server.bean.UserImgBean;
import net.wecash.server.mysql.model.UserImg;

public interface UserImageDAO {

	public UserImg getUserImage(Long userId, Integer type);

	public void deleteUserImg(Long id, String image);

	public UserImg getUserImg(Long id, String image);

	// -----------------
	public UserImg getUserIconInfo(Long id);

	public void updateUserIconInfo(Long id, String iconIndex);

	public void addUserImage(UserImg ui);

	public List<UserImgBean> getAllImgsBySQL(String sql);

	public List<UserImgBean> getAllNormalImgs(List<Long> ids);

	public List<UserImgBean> getAllIconImgs(List<Long> ids);

	public List<UserImg> getUserImages(Long userId);

	public List<String> getAllUserImg(Long id);

	public void updateUserImageInfo(Long id, Integer type, String img);

	List<UserImg> sql(String sql);

}
