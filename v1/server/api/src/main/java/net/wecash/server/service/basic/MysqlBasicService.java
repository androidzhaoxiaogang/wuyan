package net.wecash.server.service.basic;

import net.wecash.common.util.MysqlQueryUtil;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author xkk
 *
 */
public abstract class MysqlBasicService{
    @Autowired
    protected ObjectMapper mapper;
	@Autowired
	protected  SessionFactory sessionFacotry;
	
	public boolean isValueExists(String className, String field, Object value){
		Session session = sessionFacotry.getCurrentSession();
		Long count=0l;
		try {
			session.beginTransaction();
			String hql = "select count(*) from " + className + " c where c." + field + "='" + value + "'";
			Query query = session.createQuery(hql);  
			count = (Long)query.uniqueResult();  
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return count > 0 ? true:false;
	}
	
	public boolean isValueExists(String sql){
		Session session = sessionFacotry.getCurrentSession();
		Long count=0l;
		try {
			session.beginTransaction();
			Query query = session.createQuery(sql);  
			count = (Long)query.uniqueResult();  
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
		return count > 0 ? true:false;
	}
	
	public void add(Object object){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		} 
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String hql){
		Session session = sessionFacotry.getCurrentSession();
		T t=null;
		try {
			session.beginTransaction();
			t = (T)session.createQuery(hql).uniqueResult();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}  
		return t;
	}
	
	public void delete(Object object){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}  
	}
	
	public void updateByField(Object object,String whereStr){
		String hql = MysqlQueryUtil.getSingleUpdateHql(object, whereStr);
		update(hql);
	}
	
	public void update(String hql){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			session.createQuery(hql).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	public void updateBean(Object o){
		Session session = sessionFacotry.getCurrentSession();
		try {
			session.beginTransaction();
			session.update(o);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}
}
