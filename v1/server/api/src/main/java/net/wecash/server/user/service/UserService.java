package net.wecash.server.user.service;

import java.util.ArrayList;
import java.util.List;

import net.wecash.common.util.MysqlQueryUtil;
import net.wecash.server.bean.MatchRequireBean;
import net.wecash.server.bean.UserDetail;
import net.wecash.server.mysql.model.User;
import net.wecash.server.mysql.model.UserInfo;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.user.dao.UserDAO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class UserService extends MysqlBasicService implements UserDAO {

	@Override
	public void addUser(User user) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(User user) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String hql = MysqlQueryUtil.getSingleUpdateHql(user, "id");
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public User getUser(Long userId) {
		Session session = sessionFacotry.getCurrentSession();
		User mu=null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.eq("id", userId));
			Object o = c.uniqueResult();
			if(o!=null){
				mu=(User)o;
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
	public List<User> getUsers(List<Long> userIds){
		List<User> users = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(User.class);
			c.add(Restrictions.in("id", userIds));
			users = c.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public void deleteUser(Long userId) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "delete from t_user  where id=" + userId;
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public UserDetail getUserDetail(Long id) {
		UserDetail userDetill = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "select u.id as userId, u.name, u.birthday, u.gender, u.personality, u.habit, u.degree,"
					+ "u.occupation, u.state, u.type, o.min_price as minPrice,o.max_price as maxPrice, "
					+ "o.landmark as landmark,u.description as description from t_user u left join t_room_info o "
					+ "on o.user_id=u.id where u.id = " + id;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(UserDetail.class));
			Object o = query.uniqueResult();
			if(o!=null){
				 userDetill=(UserDetail) o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			
		}
		return userDetill;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDetail> getUserDetails(List<Long> ids){
		List<UserDetail> uds = new ArrayList<>();
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "select u.id as userId, u.name, u.birthday, u.gender, u.personality, u.habit, u.degree,"
					+ "u.occupation, u.state, u.type, o.min_price as minPrice,o.max_price as maxPrice, "
					+ "o.landmark as landmark,u.description as description from t_user u left join t_room_info o "
					+ "on o.user_id=u.id where u.id in (" + MysqlQueryUtil.getInIntegerList(ids) +")";
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(UserDetail.class));
			uds = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return uds;	
	}
	
	@Override
	public MatchRequireBean getMatchRequire(Long id) {
		Session session =sessionFacotry.getCurrentSession();
		MatchRequireBean matchRequireBean=null;
		try {
			session.beginTransaction();
			String sql="select u.id as userId,u.name ,u.gender ,u.birthday ,u.personality,r.min_price as minPrice,"
					+ "r.max_price as maxPrice,r.province,r.city,r.area,r.landmark "
					+ "from t_user u left join t_room_info r on u.id=r.user_id where u.id="+id;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(MatchRequireBean.class));
			Object o = (MatchRequireBean) query.uniqueResult();
			if(o!=null){
				matchRequireBean=(MatchRequireBean) o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return matchRequireBean;
	}

	@Override
	public UserDetail getUserDetailInfo(Long id) {
		UserDetail userDetill = null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "select u.id as userId, u.name, u.birthday, u.gender, u.personality, u.habit, u.degree,"
					+ " u.occupation, u.state, u.type, o.min_price as minPrice,o.max_price as maxPrice, "
					+ " l.name as landmark,a.name as area, c.name as city,p.name as province,u.description as description from t_user u left join t_room_info o "
					+ " on o.user_id=u.id LEFT JOIN t_landmark l ON l.code=o.landmark"
					+ " LEFT JOIN t_province p ON p.code=o.province"
					+ " LEFT JOIN t_area a ON a.code=o.area"
					+ " LEFT JOIN t_city c ON c.code=o.city"
					+ " where u.id = " + id;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(UserDetail.class));
			Object o = query.uniqueResult();
			if(o!=null){
				 userDetill=(UserDetail) o;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			
		}
		return userDetill;
	}

	@Override
	public UserInfo getUserInfo(Long user_id) {
		UserInfo userInfo=null;
		Session session =sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
				String sql="select u.id as id ,u.name as name,i.image as icon from t_user u LEFT JOIN t_user_image i ON u.id=i.user_id and i.type=0 where  u.id = "+user_id;
				SQLQuery query=session.createSQLQuery(sql);
				query.setResultTransformer(Transformers
						.aliasToBean(UserInfo.class));
				userInfo= (UserInfo) query.uniqueResult();
				if(userInfo.getIcon()==null){
					userInfo.setIcon("");
				}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return userInfo;
	}
	
}
