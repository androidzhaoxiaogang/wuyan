package net.wecash.server.user.service;

import java.util.List;

import net.wecash.common.auth.RoleInfo;
import net.wecash.common.util.MysqlQueryUtil;
import net.wecash.server.bean.UserPortraitResultBean;
import net.wecash.server.service.basic.MysqlBasicService;
import net.wecash.server.user.dao.PortraitDAO;
import net.wecash.server.util.BoutiqueUserUtil;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class PortraitService extends MysqlBasicService implements PortraitDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getUserId(String city, int state, int limit){
		Session session = sessionFacotry.getCurrentSession();
		List<Object> list=null;
		try {
			session.beginTransaction();
			String sql = "select r.user_id from t_user u,t_room_info r where u.id=r.user_id and u.state !=0 and u.state !=" + state;
			if (city != null) {
				sql += " and r.city =" + city;
			}
			Query query = session.createSQLQuery(sql);
			query.setMaxResults(limit);
			list = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取用户ID
	 * @param province
	 * @param city
	 * @param area
	 * @param landMark
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getUserId(String province, String city, String area,
			String landMark, Integer minPrice, Integer maxPrice, int state, int limit) {
		Session session = sessionFacotry.getCurrentSession();
		List<Object> list = null;
		try {
			session.beginTransaction();
			String sql = "select r.user_id from t_user u,t_room_info r where u.id=r.user_id and u.state !=0 and u.state !=" + state;
			if (province != null) {
				sql += " and r.province =" + province;
			}
			if (city != null) {
				sql += " and r.city=" + city;
			}
			if (area != null) {
				sql += " and r.area=" + area;
			}
			if (landMark != null && !landMark.equals("")) {
				sql += " and r.landmark=" + landMark;
			}
			if (minPrice != null) {
				sql += " and r.min_price >=" + minPrice;
			}
			if (maxPrice != null) {
				sql += " and r.min_price <=" + maxPrice;
			}
			Query query = session.createSQLQuery(sql);
			query.setMaxResults(limit);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取个性相近，年龄相仿的，行业与学历合适的
	 * 
	 * @param targetUserIds
	 * @param degree
	 * @param occupation
	 * @param startTime
	 * @param endTime
	 * @param onPersonality
	 * @param downpersonality
	 * @param limit
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<UserPortraitResultBean> getPortrait(Long userId, List<Long> targetUserIds, List<Integer> degree, 
			List<Integer> occupation, Float personality, int state, Integer limit) {
		List<UserPortraitResultBean> list = null;
		String occupationStr = MysqlQueryUtil.getInIntegerList(occupation);
		String degreeStr = MysqlQueryUtil.getInIntegerList(degree);
		
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			BoutiqueUserUtil.removeBoutiqueUser(targetUserIds);
			String sql = "select u.id as userId, u.personality, u.degree, u.occupation, u.type, u.state, u.birthday as birthday,"
					+ " u.habit as habit from t_user u where u.type=" + RoleInfo.NORMAL + " and u.id != " + userId;
/*			if(degreeStr != null){
				sql += " and  u.degree in("
						+ degreeStr
						+ ")";
			}*/
			if(occupationStr != null){
				sql += " and u.occupation in("
						+ occupationStr
						+ ") ";
			}
			if(personality != null){
				float minPersonality = personality - 0.5f;
				float maxPersonality = personality + 0.5f;
				sql += " and " + maxPersonality + "> u.personality >"
						+ minPersonality;
			}
			if (targetUserIds != null && targetUserIds.size() > 0) {
				String idStr = MysqlQueryUtil.getInIntegerList(targetUserIds);
				sql = sql + " and u.id in(" + idStr + ") ";
			}
			sql += " order by u.create_time desc";
			SQLQuery query = session.createSQLQuery(sql);
			query.setMaxResults(limit);
			query.setResultTransformer(Transformers.aliasToBean(UserPortraitResultBean.class));
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

}
