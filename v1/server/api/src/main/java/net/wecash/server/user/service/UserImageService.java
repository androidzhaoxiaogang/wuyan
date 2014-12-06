package net.wecash.server.user.service;

import java.util.List;

import net.wecash.common.util.FileUtil;
import net.wecash.common.util.MysqlQueryUtil;
import net.wecash.server.bean.UserImgBean;
import net.wecash.server.dao.FileDAO;
import net.wecash.server.mysql.model.UserImg;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.user.dao.UserImageDAO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserImageService extends MysqlBasicService implements UserImageDAO {

	@Autowired
	FileDAO imageDAO;
	@Value("${user.max.image}")
	private Integer imgNum;

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserImageService.class);

	@Override
	public UserImg getUserImage(Long userId, Integer type) {
		String hql = "select uii from UserImg uii where uii.userId=" + userId
				+ " and uii.type=" + type;
		Session session = sessionFacotry.getCurrentSession();
		UserImg userImg=null;
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			Object o = query.uniqueResult();
			if(o!=null){
				userImg=(UserImg)o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return userImg;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<UserImg> getUserImages(Long userId) {
		String hql = "select uii from UserImg uii where uii.userId=" + userId;
		Session session = sessionFacotry.getCurrentSession();
		List<UserImg> list=null;
		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public UserImg getUserImg(Long id, String image) {
		Session session = sessionFacotry.getCurrentSession();
		UserImg mu=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(UserImg.class);
			c.add(Restrictions.eq("userId", id));
			c.add(Restrictions.eq("image", image));
//			c.add(Restrictions.eq("type", FileUtil.NORMAL_IMAGE));
			Object o = c.uniqueResult();
			if(o!=null){
				mu=(UserImg)o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return mu;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllUserImg(Long id) {
		List<String> list = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "select image from t_user_image where type!=" + FileUtil.ICON + " and user_id=" + id;
			Query query = session.createSQLQuery(sql);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;

	}

	// ---------------
	
	/**
	 * include icon and normal pic
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserImgBean> getAllImgsBySQL(String sql){
		List<UserImgBean> list = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(UserImgBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}	
	
	@Override
	public List<UserImgBean> getAllNormalImgs(List<Long> ids){
		String sql = "select u.image, u.user_id as userId from t_user_image u where user_id "
				+ "in (" + MysqlQueryUtil.getInIntegerList(ids) + ") and type!=" + FileUtil.ICON;
		return getAllImgsBySQL(sql);
	}

	@Override
	public List<UserImgBean> getAllIconImgs(List<Long> ids){
		String sql = "select u.image, u.user_id as userId from t_user_image u where user_id "
				+ "in (" + MysqlQueryUtil.getInIntegerList(ids) + ") and type=" + FileUtil.ICON;
		return getAllImgsBySQL(sql);
	}
	
	@Override
	public UserImg getUserIconInfo(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		UserImg mu=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(UserImg.class);
			c.add(Restrictions.eq("userId", id));
			c.add(Restrictions.eq("type", FileUtil.ICON));
			Object o = c.uniqueResult();
			if(o!=null){
				mu=(UserImg)o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return mu;
	}

	@Override
	public void updateUserImageInfo(Long id, Integer type, String img) {
		String hql = "update " + UserImg.class.getName() + " m set m.image='"
				+ img + "'" + " where " + "m.type=" + type + " and m.userId=" + id;
		update(hql);
	}
	public void updateImageType(String image, int type){
		String hql = "update " + UserImg.class.getName() + " m set m.type="
				+ type +" where " + "m.image='" + image +"'";
		update(hql);
	}
	@Override
	public void updateUserIconInfo(Long id, String iconIndex) {
		String hql = "update " + UserImg.class.getName() + " m set m.image='"
				+ iconIndex + "'" + " where " + "m.type=" + FileUtil.ICON + " and m.userId=" + id;
		update(hql);
	}

	@Override
	public void addUserImage(UserImg ui) {
		add(ui);
	}

	@Override
	public void deleteUserImg(Long id, String image) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "delete from t_user_image  where user_id=" + id + " and image='" + image + "'";
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserImg> sql(String sql) {
		List<UserImg> list = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			list = session.createSQLQuery(sql).list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}
}
