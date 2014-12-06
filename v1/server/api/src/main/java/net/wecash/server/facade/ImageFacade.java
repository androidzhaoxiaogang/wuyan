/**
 * 
 */
package net.wecash.server.facade;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.user.dao.UserImageDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author franklin.li
 *
 */
@Service
public class ImageFacade {
	
	@Autowired
	UserImageDAO userImageDAO;
	
	@Autowired
	FileDAO fileDAO;
	@Autowired
	RestTemplate template;
	
	public UserImg getImgByType(Long id, int type){
		return userImageDAO.getUserImage(id, type);
	}
	
	public void deleteUserImg(Long id, String image) {
		userImageDAO.deleteUserImg(id, image);
	}
	
	public void saveUserImg(UserImg ui){
		userImageDAO.addUserImage(ui);
	}
	
	public UserImg getUserImgInfo(Long id, String image){
		return userImageDAO.getUserImg(id, image);
	}
	public UserImg getUserImgInfo(Long id, Integer type){
		return userImageDAO.getUserImage(id, type);
	}
	
	//icon
	
	public UserImg getUserIconInfo(Long id){
		return userImageDAO.getUserIconInfo(id);
	}
	
	public void updateUserIconInfo(Long id, String iconIndex){
		userImageDAO.updateUserIconInfo(id, iconIndex);
	}

	public void updateUserImageInfo(Long id, Integer type, String img) {
		userImageDAO.updateUserImageInfo(id,type, img);
		
	}
}
