package net.wecash.server.contact.service;

import java.util.List;

import net.wecash.common.service.Collections;
import net.wecash.server.bean.UserContactRequestBean;
import net.wecash.server.contact.dao.ContactDAO;
import net.wecash.server.mysql.model.Contact;
import net.wecash.server.mysql.model.Tag;
import net.wecash.server.service.basic.MysqlBasicService;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

@Service
public class ContactService extends MysqlBasicService implements ContactDAO {

	@Override
	public void saveUserContact(Long id, Long toUserId) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "insert into t_contact (from_user_id,to_user_id,permit) values (" + id + ","
					+ toUserId + ",0)";
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserContact(Long id, Long toUserId, int permit) {
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			String sql = "update t_contact set permit="
					+ permit + " where from_user_id=" + id + " and to_user_id="
					+ toUserId + ")";
			session.createSQLQuery(sql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public List<UserContactRequestBean> getUserContact(Long id, Integer first,
			Integer max) {
		Session session = sessionFacotry.getCurrentSession();
		List<UserContactRequestBean> list=null;
		try {
			session.beginTransaction();
			String sql = "select u.name as Name,u.gender as gender FROM t_user u, t_contact c where  u.id=c.to_user_id and c.from_user_id="
					+ id;
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers
					.aliasToBean(UserContactRequestBean.class));
			query.setFirstResult(first - 1);
			query.setMaxResults(max);
			list = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Integer> getUserId(Long id) {
		Session session = sessionFacotry.getCurrentSession();
		List<Integer> list=null;
		try {
			session.beginTransaction();
			String sql = "select to_user_id from t_contact where permit=1 and from_user_id="
					+ id;
			Query query = session.createSQLQuery(sql);
			list = query.list();
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Contact getContact(Long id, Long toUserId) {
		Session session = sessionFacotry.getCurrentSession();
		Contact contact = null;
		try {
			session.beginTransaction();
			Criteria c = session.createCriteria(Contact.class);
			c.add(Restrictions.eq("fromUserId", id));
			c.add(Restrictions.eq("toUserId", toUserId));
			Object o = c.uniqueResult();
			if (o != null) {
				contact = (Contact) o;
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.beginTransaction().rollback();
			e.printStackTrace();
		}
		return contact;
	}

	@Override
	public boolean isFirend(Long fromUserId, Long toUserId) {
		boolean state = false;
		Session session = sessionFacotry.getCurrentSession();
		Integer permit;
		Integer permit2;
		try {
			session.beginTransaction();
			String sql = "select permit from t_contact where from_user_id="
					+ fromUserId + " and to_user_id=" + toUserId;
			Query query = session.createSQLQuery(sql);
			permit = (Integer) query.uniqueResult();

			String sql2 = "select permit from t_contact where from_user_id="
					+ toUserId + " and to_user_id=" + fromUserId;
			Query query2 = session.createSQLQuery(sql2);
			permit2 = (Integer) query2.uniqueResult();

			session.getTransaction().commit();
			if (permit != null ) {
				if (permit == 1) {
					state = true;
				}
			}
			if(permit2 != null){
				if (permit2 == 1) {
					state = true;
				}
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		
		
		return state;
	}
}
