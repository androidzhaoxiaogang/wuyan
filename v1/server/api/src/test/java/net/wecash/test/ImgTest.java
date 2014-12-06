/**
 * 
 */
package net.wecash.test;

import java.util.List;

import net.wecash.server.dao.FileDAO;
import net.wecash.server.mysql.model.User;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.user.service.UserImageService;
import net.wecash.server.user.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author franklin.li
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ImgTest {
	@Autowired
	UserImageService s;
	@Autowired
	FileDAO imageDAO;
	@Autowired
	UserService user;
	@Test
	public void test()throws Exception{
//		String file = "C:\\Users\\franklin.li\\Desktop\\pic\\new\\good\\3\\";
//		try {
//			for(int j = 1;j<=4;j++){
//				InputStream is = new FileInputStream(file+j+".jpg");
//				Object o = imageDAO.addFile(new ObjectId().toString()+".jpg",is);
//				System.out.println(o.toString());
//				is.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		List<User> list = user.getUsers(null);
		for(User u : list){
			List<UserImg> images = s.getUserImages(u.getId());
			if(images != null){
				if(images.size() == 1){
					UserImg ui = images.get(0);
					s.updateImageType(ui.getImage(), 0);
				}else{
					int count = 0;
					for(UserImg ui : images){
						if(ui.getType() == 0){
							count ++;
						}
					}
					if(count >= 2){
						String image = null;
						int num = 1;
						for(UserImg ui : images){
							if(ui.getType() == 1){
								image = ui.getImage();
							}
						}
						for(UserImg ui : images){
							if(ui.getImage().equals(image)){
								s.updateImageType(ui.getImage(), 0);
							}else{
								s.updateImageType(ui.getImage(), num);
								num++;
							}
						}
					}
				}
			}
		}
	}
}
