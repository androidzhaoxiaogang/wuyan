package net.wecash.server.favorite.service;

import java.util.List;

import net.wecash.server.bean.UserCollectBean;
import net.wecash.server.favorite.dao.FavoriteDAO;
import net.wecash.server.mysql.model.Favorite;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService extends MysqlBasicService implements FavoriteDAO{
/*
	@Override
	public boolean isCollect(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		Long count=0l;
		try {
			session.beginTransaction();
			String hql = "select count(*) from Favorite where fromUserId="+id;
			Query query = session.createQuery(hql);  
			count = (Long)query.uniqueResult();  
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return count < 100 ? true:false;
		
	}*/

	@Override
	public void save(Long id, Long user_id) {
		Favorite collect=new Favorite();
		collect.setFromUserId(id);
		collect.setToUserId(user_id);
		add(collect);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserCollectBean> getFavorites(Long id, Integer cursor, Integer limit) {
		List<UserCollectBean> list=null;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "select u.id as userId, u.name, u.birthday, u.gender, "
					+ " u.occupation,  o.min_price as minPrice,o.max_price as maxPrice, "
					+ " l.name as landmark,a.name as area,i.image as icon from t_favorite c "
					+ " LEFT JOIN t_user u  on c.to_user_id =u.id"
					+ " left join t_room_info o on o.user_id=c.to_user_id"
					+ " LEFT JOIN t_landmark l ON l.code=o.landmark"
					+ " LEFT JOIN t_area a ON a.code=o.area"
					+ " LEFT JOIN t_user_image i ON i.user_id=c.to_user_id and i.type=0"
					+ " where c.from_user_id = " + id;
			Query query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.aliasToBean(UserCollectBean.class));
			query.setFirstResult(cursor);
			query.setMaxResults(limit);
			list = query.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			
		}
		return list;
	}

	@Override
	public boolean isFavorite(Long userId, Long toUserId) {
		boolean state=false;
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Favorite.class);
			c.add(Restrictions.eq("fromUserId", userId));
			c.add(Restrictions.eq("toUserId", toUserId));
			Object o = c.uniqueResult();
			if (o != null) {
				state=true;
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return state;
	}
	@Override
	public void delete(Long id, Long targetId) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql="delete from t_favorite where from_user_id="+id+" and to_user_id="+targetId;
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	@Override
	public void delete(long id){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql="delete from t_favorite where from_user_id="+id+" or to_user_id="+id;
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
