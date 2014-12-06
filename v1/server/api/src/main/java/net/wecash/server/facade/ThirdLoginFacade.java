package net.wecash.server.facade;

import java.util.Date;
import java.util.Map;

import net.wecash.common.util.FileUtil;
import net.wecash.server.bean.ThirdLoginBean;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.mysql.model.SnsAuthInfo;
import net.wecash.server.mysql.model.User;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.service.basic.MysqlBasicAtomService;
import net.wecash.server.sns.dao.ThirdSnsRequestDAO;

import org.apache.commons.collections.MapUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ThirdLoginFacade extends MysqlBasicAtomService {
	@Autowired
	ThirdSnsRequestDAO loginRequestDAO;
	@Autowired
	FileDAO imageDAO;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	protected SessionFactory sessionFacotry;
	@Autowired
	PushFacade pushFacade;

	public User TransactionManage(String uid, String access_token,
			Map<String, Object> weiboInfo, ThirdLoginBean loginBean) {
		User u = new User();
		Session session=sessionFacotry.getCurrentSession();
		session.beginTransaction();
		try {
			SnsAuthInfo sai = new SnsAuthInfo();
			sai.setThirdId(uid);
			sai.setThirdToken(access_token);
			sai.setType(loginBean.getType());
			u.setGender(MapUtils.getString(weiboInfo, "gender"));
			u.setName(MapUtils.getString(weiboInfo, "name"));
			u.setDescription(MapUtils.getString(weiboInfo, "description"));
			u.setType(1);
			u.setState(0);
			u.setCreateTime(new Date());
			save(u);
			sai.setUserId(u.getId());
			save(sai);
			if (weiboInfo.get("profile_image_url") != null) {
				String url = MapUtils.getString(weiboInfo, "profile_image_url");
				Object imageId = imageDAO.addFile(loginRequestDAO.getImage(url));
				save(new UserImg(u.getId(), imageId.toString(), FileUtil.ICON));
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return u;

	}
}
